package com.xy.service;

import com.xy.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author x1yyy
 */
public interface UserService {

    /**
     * 根据用户账号查询用户
     * @param account 用户账号
     * @param password 用户密码
     * @return 查询到的用户主键
     */
    int queryUserByAccount(String account, String password);

    /**
     * 添加用户
     * @param account 账号
     * @param password 密码
     * @return 是否添加成功
     */
    int addUser(String account, String password);
}
