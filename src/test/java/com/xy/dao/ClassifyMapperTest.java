package com.xy.dao;

import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClassifyMapperTest extends TestCase {

    public void testAddClassify() {
    }

    public void testQueryClassifyKey() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        ClassifyMapper classifyMapper = applicationContext.getBean("classifyMapper", ClassifyMapper.class);

        System.out.println(classifyMapper.queryClassifyKey("人"));
    }
}