package com.xy.service.impl;

import com.xy.dao.ClassifyMapper;
import com.xy.dao.PictureMapper;
import com.xy.pojo.Classify;
import com.xy.pojo.Picture;
import com.xy.service.PictureService;
import com.xy.utils.ClassifyUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
    private ClassifyMapper classifyMapper;

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
                name = checkRepeat(name, userKey);

                double size = Double.parseDouble(String.valueOf(file.getSize()));

                try {
                    file.transferTo(new File(folder, Objects.requireNonNull(name)));
                    Thumbnails.of(path + name).scale(1F).outputQuality(0.3F).toFile(thumbPath + name);

                    String classify = ClassifyUtil.getClassify(thumbUrl + name);
                    int classifyKey = classifyMapper.queryClassifyKey(classify);

                    if (classifyKey == -1) {
                        classifyKey = (int) (Math.random() * (999999 - 100000) + 100000);
                        classifyMapper.addClassify(new Classify(classifyKey, classify));
                    }

                    map.put("name", name);
                    map.put("imgUrl", imgUrl + name);
                    map.put("thumbUrl", thumbUrl + name);
                    map.put("date", new Date());
                    map.put("size", size);
                    map.put("classifyKey", classifyKey);
                    map.put("userKey", userKey);

                    results.put(oldName, pictureMapper.addPicture(map));
                } catch (Exception e) {
                    results.put(oldName, -1);
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
    public Map<String, Integer> deletePicture(List<String> pictureNames, int userKey) {
        Map<String, Integer> map = new HashMap<>();

        for (String name : pictureNames) {
            map.put(name, pictureMapper.deletePicture(name, userKey));
        }
        
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
}
