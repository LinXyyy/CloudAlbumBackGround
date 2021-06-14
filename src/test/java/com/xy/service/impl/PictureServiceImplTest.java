package com.xy.service.impl;

import com.xy.pojo.Picture;
import com.xy.service.PictureService;
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

        for (Picture picture : pictureService.getPictureByUserKeyAndClassify(1, 1)) {
            System.out.println(picture);
        }
    }

    public void testAddPictures() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        PictureService pictureService = applicationContext.getBean("pictureServiceImpl", PictureService.class);

        Map<String, Object> map = new HashMap<>();
        map.put("pictureKey", 3);
        map.put("name", "3");
        map.put("imgUrl", "3");
        map.put("date", new Date());
        map.put("size", 3);
        map.put("classify", 3);
        map.put("userKey", 3);

    }

    public void testDeletePictures() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        PictureService pictureService = applicationContext.getBean("pictureServiceImpl", PictureService.class);

        List<String> list = new ArrayList<>();
        list.add("1.jpg");
        list.add("5.jpg");

        System.out.println(pictureService.deletePicture(list, 1));
    }

    public void testCheckRepeat() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        PictureService pictureService = applicationContext.getBean("pictureServiceImpl", PictureService.class);

        System.out.println(pictureService.checkRepeat("2.jpg", 1));
    }
}