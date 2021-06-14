package com.xy.dao;

import com.xy.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author x1yyy
 */
public interface UserMapper {

    /**
     * 根据用户账号查询用户
     * @param account 用户账号
     * @return 查询到的用户
     */
    User queryUserByAccount(@Param("account") String account);

    /**
     * 添加用户
     * @param user 用户
     * @return 是否添加成功
     */
    int addUser(User user);
}
