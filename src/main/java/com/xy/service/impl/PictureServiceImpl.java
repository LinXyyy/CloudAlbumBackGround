package com.xy.service.impl;

import com.xy.dao.PictureMapper;
import com.xy.pojo.Classify;
import com.xy.pojo.Picture;
import com.xy.service.ClassifyService;
import com.xy.service.PictureService;
import com.xy.utils.Base64Util;
import com.xy.utils.ClassifyUtil;
import com.xy.utils.FaceUtil;
import com.xy.utils.FileUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author x1yyy
 */
@Service
@SuppressWarnings("all")
public class PictureServiceImpl implements PictureService {

    private PictureMapper pictureMapper;
    private ClassifyService classifyService;
    private FaceUtil faceUtil;

    @Autowired
    public PictureServiceImpl(PictureMapper pictureMapper, ClassifyService classifyService, FaceUtil faceUtil) {
        this.pictureMapper = pictureMapper;
        this.classifyService = classifyService;
        this.faceUtil = faceUtil;
    }

    @Override
    public Map<String, Object> getPictureByUserKey(int userKey) {
        Map<String, Object> map = new HashMap<>();

        List<Picture> pictures = pictureMapper.getPictureByUserKey(userKey);

        if (pictures.size() == 0) {
            map.put("resultCode", 1);
            map.put("resultMessage","photo album is empty");
            map.put("data", null);
        } else {
            map.put("resultCode", 0);
            map.put("resultMessage","success");
            map.put("data", pictures);
        }

        return map;
    }

    @Override
    public Map<String, Object> getPictureByUserKeyAndClassify(int userKey, int classifyKey) {
        Map<String, Object> map = new HashMap<>();

        map.put("resultCode", 0);
        map.put("resultMessage","success");
        map.put("data", pictureMapper.getPictureByUserKeyAndClassify(userKey, classifyKey));

        return map;
    }

    @Override
    public Map<String, Map<String, Object>> addPicture(MultipartFile[] img, int userKey, String imgUrl, String thumbUrl) {

        Map<String, Map<String, Object>> results = new HashMap<>();
        Map<String, Object> map = new HashMap<>();

        String path = "/www/CloudAlbum/" + userKey + "/";
        String thumbPath = path + "thumb/";

        File folder = new File(path);
        if (!folder.exists()) {
            Boolean bool = folder.mkdirs();
        }

        File thumbFolder = new File(thumbPath);
        if (!thumbFolder.exists()) {
            Boolean bool = thumbFolder.mkdirs();
        }

        CountDownLatch countDownLatch = new CountDownLatch(img.length);
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        for (MultipartFile file : img) {

            Runnable task = () -> {
                // 照片信息
                String name = new String(Objects.requireNonNull(file.getOriginalFilename()).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                String oldName = name;
                String faceKey = null;
                name = checkRepeat(name, userKey);

                double size = Double.parseDouble(String.valueOf(file.getSize()));

                try {
                    file.transferTo(new File(folder, Objects.requireNonNull(name)));

                    Thumbnails.of(new File(path + name)).scale(1F).outputQuality(0.3F).toFile(new File(thumbPath + name));

                    String classify = ClassifyUtil.getClassify(thumbUrl + name);

                    if (classify.indexOf("人") != -1) {
                        faceKey = faceUtil.query(path + name);
                        faceUtil.add(path + name, faceKey);
                    }

                    int classifyKey = classifyService.queryClassifyKey(classify);

                    if (classifyKey == 0) {
                        classifyKey = (int) (Math.random() * (999999 - 100000) + 100000);
                        classifyService.addClassify(new Classify(classifyKey, classify, userKey));
                    }

                    map.put("name", name);
                    map.put("imgUrl", imgUrl + name);
                    map.put("thumbUrl", thumbUrl + name);
                    map.put("date", new Date());
                    map.put("size", size);
                    map.put("classifyKey", classifyKey);
                    map.put("faceKey", faceKey);
                    map.put("userKey", userKey);

                    Map<String, Object> result = new HashMap<>();

                    if (pictureMapper.addPicture(map) == 1) {
                        if (oldName.equals(name)) {
                            result.put("resultCode", 0);
                            result.put("name", oldName);
                            result.put("resultMessage", "success");
                            results.put(name, result);
                        } else {
                            result.put("name", oldName);
                            result.put("resultCode", 1);
                            result.put("resultMessage", "rewrite picture name to " + name);
                            results.put(name, result);
                        }
                    } else {
                        result.put("name", oldName);
                        result.put("resultCode", 3);
                        result.put("resultMessage", "database write failed");
                        results.put(name, result);
                    }
                } catch (IOException ioException) {
                    Map<String, Object> result = new HashMap<>();
                    result.put("name", oldName);
                    result.put("resultCode", 2);
                    result.put("resultMessage", "file write failed");
                    results.put(oldName, result);
                } finally {
                    countDownLatch.countDown();
                }
            };

            executorService.execute(task);
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();

        return results;
    }

/*
* 存在优化问题：删除本地文件与删除数据库文件的先后顺序，如果delete存在这个问题，那么add也会存在这个问题
* */
    @Override
    public Map<String, Map<String, Object>> deletePicture(List<String> pictureNames, int userKey) {
        Map<String, Map<String, Object>> results = new HashMap<>();

        CountDownLatch countDownLatch = new CountDownLatch(pictureNames.size());
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        for (String name : pictureNames) {

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    String path = "/www/CloudAlbum/" + userKey + "/" + name;
                    File file = new File(path);

                    String thumbPath = "/www/CloudAlbum/" + userKey + "/thumb/" + name;
                    File thumbFile = new File(thumbPath);

                    Map<String, Object> result = new HashMap<>();

                    if (!file.delete() || !thumbFile.delete()) {
                        result.put("name", name);
                        result.put("resultCode", 2);
                        result.put("resultMessage", "file delete failed");
                        results.put(name, result);
                    }
                    else if (pictureMapper.deletePicture(name, userKey) != 1) {
                        result.put("name", name);
                        result.put("resultCode", 1);
                        result.put("resultMessage", "database delete failed");
                        results.put(name, result);
                    }
                    else {
                        result.put("name", name);
                        result.put("resultCode", 0);
                        result.put("resultMessage", "success");
                        results.put(name, result);
                    }

                    countDownLatch.countDown();
                }
            };

            executorService.execute(runnable);
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();

        return results;
    }

    @Override
    public String checkRepeat(String name, int userKey) {
        // 将name进行处理进行模糊查询
        int index = name.lastIndexOf(".");
        String checkName = name.substring(0, index) + "%" + name.substring(index);

        // 检查照片名称是否重复
        String existName = pictureMapper.queryName(checkName, userKey);

        // 数据库中存在相同名称的照片
        if (existName != null) {
            int pointIndex = existName.lastIndexOf(".");
            int numberIndex = existName.lastIndexOf("-");
            try {
                if (numberIndex == -1) {
                    // 只存在一个名称相同的照片未进行编号,后缀为1
                    name = name.substring(0,pointIndex) + "-1" + name.substring(pointIndex);
                } else {
                    if (existName.equals(name)) {
                        // 存在名称相同的照片且照片名称本来就存在数字后缀
                        name = name.substring(0,pointIndex) + "-1" + name.substring(pointIndex);
                    } else {
                        // 存在名称相同的照片且存在后缀,后缀加1
                        int number = Integer.parseInt(existName.substring(numberIndex + 1,pointIndex)) + 1;
                        name = existName.substring(0,numberIndex + 1) + number + existName.substring(pointIndex);
                    }
                }
            } catch (NumberFormatException e) {
                // 只存在一个名称相同的照片未进行编号,且存在字符串“-”转换为int失败,后缀为1
                name = name.substring(0,pointIndex) + "-1" + name.substring(pointIndex);
            }
        }
        return name;
    }

    @Override
    public Map<String, Object> faceRetrieval(MultipartFile img, String userKey) {
        Map<String, Object> map = new HashMap<>();

        String name = img.getOriginalFilename();

        File file = new File("/www/CloudAlbum", name);

        try {
            FileUtils.copyInputStreamToFile(img.getInputStream(), file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Tencent*/
        String classify = ClassifyUtil.getClassify(file);

        if (classify.indexOf("人") != -1) {
            /*Baidu*/
            String faceKey = faceUtil.query(file.getPath());

            List<Picture> pictures = pictureMapper.getPictureByUserKeyAndFaceKey(faceKey, Integer.parseInt(userKey));

            if (pictures.size() <= 0) {
                map.put("resultCode", 1);
                map.put("resultMessage", "no match face in album");
                map.put("date", null);
            } else {
                map.put("resultCode", 0);
                map.put("resultMessage", "success");
                map.put("date", pictures);
            }

            file.delete();
            return map;
        }

        file.delete();
        map.put("resultCode", 2);
        map.put("resultMessage", "picture not find face");
        map.put("data", null);
        return map;
    }
}
