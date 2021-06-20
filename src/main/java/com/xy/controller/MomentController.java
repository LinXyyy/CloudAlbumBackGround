package com.xy.controller;

import com.xy.pojo.Picture;
import com.xy.service.MomentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author x1yyy
 */
@Controller
public class MomentController {

    @Autowired
    private MomentService momentService;

    @ResponseBody
    @RequestMapping("/createMoment")
    public Map<String, Object> createMoment(@RequestHeader String userKey) {
        return momentService.createMoment(userKey);
    }

    @ResponseBody
    @RequestMapping("/saveMoment")
    public Map<String, Object> saveMoment(String id,@RequestHeader String userKey) {
        return momentService.saveMoment(id, userKey);
    }
}
