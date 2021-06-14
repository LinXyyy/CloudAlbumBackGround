package com.xy.controller;

import com.xy.pojo.Picture;
import com.xy.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public List<Picture> getPictureByUserKey(HttpServletRequest request) {
        int userKey = Integer.parseInt(request.getHeader("userKey"));
        return pictureService.getPictureByUserKey(userKey);
    }

    @ResponseBody
    @RequestMapping("/getPictureByUserKeyAndClassify")
    public List<Picture> getPictureByUserKeyAndClassify(String classifyKey, HttpServletRequest request) {
        int userKey = Integer.parseInt(request.getHeader("userKey"));
        return pictureService.getPictureByUserKeyAndClassify(userKey, Integer.parseInt(classifyKey));
    }

    @ResponseBody
    @RequestMapping("/addPicture")
    public Map<String, Integer> addPicture(MultipartFile[] img, HttpServletRequest request) {
        int userKey = Integer.parseInt(request.getHeader("userKey"));
        String imgUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/image/"+ userKey + "/";
        String thumbUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/image/"+ userKey + "/thumb/";

        System.out.println(imgUrl);

        return pictureService.addPicture(img, userKey, imgUrl, thumbUrl);
    }

    @ResponseBody
    @RequestMapping("/deletePicture")
    public Map<String, Integer> deletePicture(List<String> pictureNames, HttpServletRequest request) {
        int userKey = Integer.parseInt(request.getHeader("userKey"));

        return pictureService.deletePicture(pictureNames, userKey);
    }
}
