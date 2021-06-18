package com.xy.dao;

import com.xy.pojo.Classify;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    int queryClassifyKey(@Param("classify") String classify);

    /**
     * 查询指定用户下所有的分类
     * @param userKey 指定用户
     * @return 结果
     */
    List<Classify> queryAllClassify(@Param("userKey") String userKey);
}
