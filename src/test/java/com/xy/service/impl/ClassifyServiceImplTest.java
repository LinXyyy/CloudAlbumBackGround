package com.xy.service.impl;

import com.xy.pojo.Classify;
import com.xy.service.ClassifyService;
import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClassifyServiceImplTest extends TestCase {

    public void testAddClassify() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        ClassifyService classifyService = applicationContext.getBean("classifyServiceImpl", ClassifyService.class);

        System.out.println(classifyService.addClassify(new Classify(4, "4")));
    }

    public void testQueryClassifyKey() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        ClassifyService classifyService = applicationContext.getBean("classifyServiceImpl", ClassifyService.class);

        System.out.println(classifyService.queryClassifyKey("3"));
    }
}