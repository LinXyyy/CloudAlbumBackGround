package com.xy.controller;

import com.xy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author x1yyy
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/validateUser")
    public int userLogin(String account, String password) {
        return userService.queryUserByAccount(account, password);
    }

    @ResponseBody
    @RequestMapping("/registerUser")
    public int registerUser(String account, String password) {
        return userService.addUser(account, password);
    }
}
