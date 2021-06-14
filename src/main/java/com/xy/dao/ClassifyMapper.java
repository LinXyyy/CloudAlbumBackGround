package com.xy.dao;

import com.xy.pojo.Classify;

/**
 * @author x1yyy
 */
public interface ClassifyMapper {

    /**
     * 添加分类
     * @param classify 分类
     * @return 是否成功
     */
    int addClassify(Classify classify);

    /**
     * 获取某个分类的key
     * @param classify 分类
     * @return key
     */
    int queryClassifyKey(String classify);
}
