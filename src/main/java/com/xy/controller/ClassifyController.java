package com.xy.controller;

import com.xy.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author x1yyy
 */
@Controller
public class ClassifyController {

    @Autowired
    ClassifyService classifyService;

    @ResponseBody
    @RequestMapping("/getAllClassify")
    public Map<String, Object> getAllClassify(@RequestHeader String userKey) {
        return classifyService.queryAllClassify(userKey);
    }

}
