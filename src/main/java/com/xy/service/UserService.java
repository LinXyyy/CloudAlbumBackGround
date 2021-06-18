package com.xy.service;

import com.xy.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author x1yyy
 */
public interface UserService {

    /**
     * 根据用户账号查询用户
     * @param account 用户账号
     * @param password 用户密码
     * @return
     * {
     *     "resultCode": 1 or 0;
     *     "resultMessage": "account or password is error" or "success",
     *     "data": null or userKey
     * }
     */
    Map<String, Object> queryUserByAccount(String account, String password);

    /**
     * 添加用户
     * @param account 账号
     * @param password 密码
     * @return
     * {
     *     "resultCode": 1 or 0,
     *     "resultMessage": "account is already existed" or "success,
     *     "data": null or 935960
     * }
     */
    Map<String, Object> addUser(String account, String password);
}
