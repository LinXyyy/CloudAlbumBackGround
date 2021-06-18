package com.xy.controller;

import com.xy.pojo.Picture;
import com.xy.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author x1yyy
 */
@Controller
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @ResponseBody
    @RequestMapping("/getPictureByUserKey")
    public List<Picture> getPictureByUserKey(@RequestHeader String userKey) {
        return pictureService.getPictureByUserKey(Integer.parseInt(userKey));
    }

    @ResponseBody
    @RequestMapping("/getPictureByUserKeyAndClassify")
    public List<Picture> getPictureByUserKeyAndClassify(String classifyKey, @RequestHeader String userKey) {
        return pictureService.getPictureByUserKeyAndClassify(Integer.parseInt(userKey), Integer.parseInt(classifyKey));
    }

    @ResponseBody
    @RequestMapping("/addPicture")
    public Map<String, Integer> addPicture(MultipartFile[] img, HttpServletRequest request) {
        int userKey = Integer.parseInt(request.getHeader("userKey"));

        String imgUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/image/"+ userKey + "/";
        String thumbUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/image/"+ userKey + "/thumb/";

        return pictureService.addPicture(img, userKey, imgUrl, thumbUrl);
    }

    @ResponseBody
    @RequestMapping("/deletePicture")
    public Map<String, Integer> deletePicture(String[] pictureNames, @RequestHeader String userKey) {
        return pictureService.deletePicture(pictureNames, Integer.parseInt(userKey));
    }

    @ResponseBody
    @RequestMapping("/faceRetrieval")
    public Map<String, Object> faceRetrieval(MultipartFile img, @RequestHeader String userKey) {
        return pictureService.faceRetrieval(img, userKey);
    }
}
