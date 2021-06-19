package com.xy.service.impl;

import com.xy.pojo.Picture;
import com.xy.service.MomentService;
import junit.framework.TestCase;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;

public class MomentServiceImplTest extends TestCase {

    public void testCreateMoment() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        MomentService momentService = applicationContext.getBean("momentServiceImpl", MomentService.class);

        Map<String, Object> moment = momentService.createMoment("1");

        for (String s : moment.keySet()) {
            System.out.println(s + "," + moment.get(s));
        }
    }
}