package com.xy.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
        "classpath:applicationContext.xml"
})
public class FaceUtilTest {

    @Autowired
    FaceUtil faceUtil;

    @Test
    public void testAdd() {
        System.out.println(faceUtil.add("C:\\Users\\x1yyy\\Pictures\\Camera Roll\\ycy2.png", "ycy"));
    }

    @Test
    public void testQuery() {
        System.out.println(faceUtil.query("C:\\Users\\x1yyy\\Pictures\\Camera Roll\\ycy.jpg"));
    }

    @Test
    public void testFaceDetect() {
        System.out.println(faceUtil.faceDetect("C:\\Users\\x1yyy\\Pictures\\Camera Roll\\ada.jpg"));
    }
}
