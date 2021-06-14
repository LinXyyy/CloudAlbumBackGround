package com.xy.controller;

import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserControllerTest extends TestCase {

    public void testUserLogin() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserController userController = applicationContext.getBean("userController", UserController.class);

        System.out.println(userController.userLogin("123", "123"));
    }
}