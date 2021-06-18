package com.xy.service;

import com.xy.pojo.Classify;
import org.apache.ibatis.binding.BindingException;

import java.util.Map;

/**
 * @author x1yyy
 */
public interface ClassifyService {

    /**
     * 添加分类
     * @param classify 分类
     * @return 是否成功
     */
    int addClassify(Classify classify);

    /**
     * 获取某个分类的key
     * @param classify 分类
     * @throws BindingException 查询不存在的分类结果为null注入int的异常
     * @return key
     */
    int queryClassifyKey(String classify) throws BindingException;

    /**
     * 查询指定用户下所有的分类
     * @param userKey 指定用户
     * @return 结果
     */
    Map<String, Object> queryAllClassify(String userKey);
}
