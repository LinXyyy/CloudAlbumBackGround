package com.xy.service.impl;

import com.xy.dao.ClassifyMapper;
import com.xy.pojo.Classify;
import com.xy.service.ClassifyService;
import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
