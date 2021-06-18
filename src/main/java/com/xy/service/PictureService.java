package com.xy.service;

import com.xy.pojo.Picture;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 *
 * @author x1yyy
 */
public interface PictureService {

    /**
     * 根据用户主键获取该用户下的所有照片
     *
     * @param userKey 用户主键
     * @return 查询到照片的列表
     */
    Map<String, Object> getPictureByUserKey(int userKey);

    /**
     * 根据用户主键以及所属分类查询照片
     *
     * @param userKey 用户主键
     * @param classifyKey 所属分类
     * @return 查询到照片的列表
     */
    Map<String, Object> getPictureByUserKeyAndClassify(int userKey, int classifyKey);

    /**
     * 添加照片
     *
     * @param img 照片
     * @param userKey 用户主键
     * @param imgUrl 照片路径
     * @param thumbUrl 缩略图路径
     * @return 添加是否成功
     */
    Map<String, Map<String, Object>> addPicture(MultipartFile[] img, int userKey, String imgUrl, String thumbUrl);

    /**
     * 删除照片
     *
     * @param pictureName 需要删除的照片的主键
     * @param userKey 用户主键
     * @return 是否删除
     */
    Map<String, Map<String, Object>> deletePicture(String[] pictureName, int userKey);

    /**
     * 查询所查照片名是否存在
     *
     * @param name 所查照片名
     * @param userKey 照片所属用户
     * @return 查询结果
     */
    String checkRepeat(String name, int userKey);

    /**
     * 人脸检索
     * 用户上传一张人脸照片,查询该人脸的所有照片
     * @param img 上传的人脸照片
     * @param userKey 照片所属用户
     * @return 查询结果
     */
    Map<String, Object> faceRetrieval(MultipartFile img, String userKey);
}
