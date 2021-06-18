package com.xy.service.impl;

import com.xy.dao.ClassifyMapper;
import com.xy.pojo.Classify;
import com.xy.service.ClassifyService;
import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author x1yyy
 */
@Service
public class ClassifyServiceImpl implements ClassifyService {

    @Autowired
    private ClassifyMapper classifyMapper;

    @Override
    public int addClassify(Classify classify) {
        return classifyMapper.addClassify(classify);
    }

    @Override
    public int queryClassifyKey(String classify) {
        try {
            return classifyMapper.queryClassifyKey(classify);
        } catch (BindingException bindingException) {
            return 0;
        }
    }

    @Override
    public Map<String, Object> queryAllClassify(String userKey) {
        Map<String, Object> result = new HashMap<>();

        List<Classify> classifies = classifyMapper.queryAllClassify(userKey);

        if (classifies.size() == 0) {
            result.put("resultCode", 1);
            result.put("resultMessage", "classify is null");
            result.put("data", null);
        }
        else {
            result.put("resultCode", 0);
            result.put("resultMessage", "success");
            result.put("data", classifies);
        }

        return result;
    }
}
