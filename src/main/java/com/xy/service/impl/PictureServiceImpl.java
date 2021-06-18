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

    @Autowired
    private PictureMapper pictureMapper;
    @Autowired
    private ClassifyService classifyService;

    @Override
    public List<Picture> getPictureByUserKey(int userKey) {
        return pictureMapper.getPictureByUserKey(userKey);
    }

    @Override
    public List<Picture> getPictureByUserKeyAndClassify(int userKey, int classifyKey) {
        return pictureMapper.getPictureByUserKeyAndClassify(userKey, classifyKey);
    }

    @Override
    public Map<String, Integer> addPicture(MultipartFile[] img, int userKey, String imgUrl, String thumbUrl) {
        Map<String, Integer> results = new HashMap<>();

        Map<String, Object> map = new HashMap<>();

        CountDownLatch countDownLatch = new CountDownLatch(img.length);
        ExecutorService executorService = Executors.newSingleThreadExecutor();

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
                        FaceUtil faceUtil = new FaceUtil();
                        faceKey = faceUtil.query(path + name);
                        faceUtil.add(path + name, faceKey);
                    }

                    int classifyKey = classifyService.queryClassifyKey(classify);

                    if (classifyKey == 0) {
                        classifyKey = (int) (Math.random() * (999999 - 100000) + 100000);
                        classifyService.addClassify(new Classify(classifyKey, classify));
                    }

                    map.put("name", name);
                    map.put("imgUrl", imgUrl + name);
                    map.put("thumbUrl", thumbUrl + name);
                    map.put("date", new Date());
                    map.put("size", size);
                    map.put("classifyKey", classifyKey);
                    map.put("faceKey", faceKey);
                    map.put("userKey", userKey);

                    results.put(oldName, pictureMapper.addPicture(map));
                } catch (Exception e) {
                    results.put(oldName, -1);
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

    @Override
    public Map<String, Integer> deletePicture(String[] pictureNames, int userKey) {
        Map<String, Integer> map = new HashMap<>();

        CountDownLatch countDownLatch = new CountDownLatch(pictureNames.length);
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        for (String name : pictureNames) {

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    String path = "/www/CloudAlbum/" + userKey + "/" + name;
                    File file = new File(path);

                    String thumbPath = "/www/CloudAlbum/" + userKey + "/thumb/" + name;
                    File thumbFile = new File(thumbPath);

                    file.delete();
                    thumbFile.delete();

                    try {
                        if (file.delete() && thumbFile.delete() && pictureMapper.deletePicture(name, userKey) == 1) {
                            map.put(name, 1);
                        } else {
                            map.put(name, -1);
                        }
                    } catch (Exception e) {
                        map.put(name, -1);
                    } finally {
                        countDownLatch.countDown();
                    }
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

        return map;
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
            String faceKey = new FaceUtil().query(file.getPath());

            map.put("resultCode", 1);
            map.put("result", "查询成功");
            map.put("pictures", pictureMapper.getPictureByUserKeyAndFaceKey(faceKey, Integer.parseInt(userKey)));
            file.delete();
            return map;
        }

        file.delete();
        map.put("resultCode", -1);
        map.put("result", "图片未检测到人脸");
        return map;
    }
}
