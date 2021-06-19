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
     */
    Map<String, Object> createMoment(String userKey);
}
