package com.xy.service.impl;

import com.xy.dao.UserMapper;
import com.xy.pojo.User;
import com.xy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author x1yyy
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, Object> queryUserByAccount(String account, String password) {
        Map<String, Object> map = new HashMap<>();

        User user = userMapper.queryUserByAccount(account);

        if (user == null || !user.getPassword().equals(password)) {
            map.put("resultCode", 1);
            map.put("resultMessage", "account or password is error");
            map.put("data", null);
        } else {
            map.put("resultCode", 0);
            map.put("resultMessage", "success");
            map.put("data", user.getUserKey());
        }

        return map;
    }

    @Override
    public Map<String, Object> addUser(String account, String password) {
        Map<String, Object> map = new HashMap<>();
        int userKey;

        if (userMapper.queryUserByAccount(account) != null) {
            map.put("resultCode", 1);
            map.put("resultMessage", "account is already existed");
            map.put("data", null);
        } else {
            userKey = (int) (Math.random() * (999999 - 100000) + 100000);

            map.put("resultCode", 0);
            map.put("resultMessage", "success");
            map.put("data", userKey);

            userMapper.addUser(new User(userKey, account, password));
        }

        return map;
    }
}
