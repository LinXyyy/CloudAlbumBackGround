package com.xy.service;

import com.xy.pojo.Picture;

import java.util.List;
import java.util.Map;

/**
 * @author x1yyy
 */
public interface MomentService {

    /**
     * 系统自动生成精彩时刻
     * @param userKey 所属用户
     * @return 结果集
     */
    Map<String, Object> createMoment(String userKey);

    /**
     * 保存精彩时刻
     * @param id 精彩时刻的标识符
     * @param userKey 用户
     * @return 成功或失败
     */
    Map<String, Object> saveMoment(String id, String userKey);
}
