package com.xy.service.impl;

import com.xy.dao.ClassifyMapper;
import com.xy.pojo.Classify;
import com.xy.pojo.Picture;
import com.xy.service.PictureService;
import com.xy.utils.ClassifyUtil;
import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

public class PictureServiceImplTest extends TestCase {

    public void testGetPictureByUserKey() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        PictureService pictureService = applicationContext.getBean("pictureServiceImpl", PictureService.class);

        System.out.println(pictureService.getPictureByUserKey(0));
    }

    public void testGetPictureByUserKeyAndClassify() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        PictureService pictureService = applicationContext.getBean("pictureServiceImpl", PictureService.class);

        pictureService.getPictureByUserKeyAndClassify(762736, 0);
    }

    public void testAddPictures() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        PictureService pictureService = applicationContext.getBean("pictureServiceImpl", PictureService.class);
        ClassifyMapper classifyMapper = applicationContext.getBean("classifyMapper", ClassifyMapper.class);


        String classify = ClassifyUtil.getClassify("http://49.234.149.121:8080/image/762736/thumb/DetectLabel1.jpg");
        int classifyKey = classifyMapper.queryClassifyKey(classify);

        if (classifyKey == -1) {
            classifyKey = (int) (Math.random() * (999999 - 100000) + 100000);
            classifyMapper.addClassify(new Classify(classifyKey, classify, 1));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("name", "DetectLabel1.jpg");
        map.put("imgUrl", "http://49.234.149.121:8080/image/762736/DetectLabel1.jpg");
        map.put("thumbUrl", "http://49.234.149.121:8080/image/762736/thumb/DetectLabel1.jpg");
        map.put("date", new Date());
        map.put("size", 1);
        map.put("classifyKey", classifyKey);
        map.put("userKey", 306205);
    }

    public void testDeletePictures() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        PictureService pictureService = applicationContext.getBean("pictureServiceImpl", PictureService.class);

//        String[] strings = {"1"};
//        System.out.println(pictureService.deletePicture(strings, 1));

    }

    public void testCheckRepeat() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        PictureService pictureService = applicationContext.getBean("pictureServiceImpl", PictureService.class);

        System.out.println(pictureService.checkRepeat("qwfraw.jpg", 306205));
    }
}