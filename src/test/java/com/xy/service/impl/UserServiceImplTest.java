package com.xy.service.impl;

import com.xy.pojo.User;
import com.xy.service.UserService;
import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceImplTest extends TestCase {

    public void testQueryUserByAccount() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = applicationContext.getBean("userServiceImpl", UserService.class);
        System.out.println(userService.queryUserByAccount("123", "123"));
    }

    public void testAddUser() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = applicationContext.getBean("userServiceImpl", UserService.class);

        System.out.println(userService.addUser(",",","));
    }
}