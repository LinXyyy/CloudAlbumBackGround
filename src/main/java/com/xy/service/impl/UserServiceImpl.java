package com.xy.service.impl;

import com.xy.dao.UserMapper;
import com.xy.pojo.User;
import com.xy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author x1yyy
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int queryUserByAccount(String account, String password) {
        User user = userMapper.queryUserByAccount(account);
        return user.getPassword().equals(password) ? user.getUserKey() : -1;
    }

    @Override
    public int addUser(String account, String password) {
        if (userMapper.queryUserByAccount(account) != null) {
            return -1;
        }

        int userKey = (int) (Math.random() * (999999 - 100000) + 100000);
        userMapper.addUser(new User(userKey, account, password));
        return userKey;
    }
}
