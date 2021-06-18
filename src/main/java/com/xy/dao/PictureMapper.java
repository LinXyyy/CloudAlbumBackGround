package com.xy.dao;

import com.xy.pojo.Picture;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author x1yyy
 *
 */
public interface PictureMapper {

    /**
     * 根据用户主键获取该用户下的所有照片
     *
     * @param userKey 用户主键
     * @return 查询到照片的列表
     */
    List<Picture> getPictureByUserKey(@Param("userKey") int userKey);

    /**
     * 根据用户主键以及所属分类查询照片
     *
     * @param userKey 用户主键
     * @param classifyKey 所属分类
     * @return 查询到照片的列表
     */
    List<Picture> getPictureByUserKeyAndClassify(@Param("userKey") int userKey, @Param("classifyKey") int classifyKey);

    /**
     * 添加照片
     *
     * @param picture 照片的信息
     * @return 添加是否成功
     */
    int addPicture(Map<String, Object> picture);

    /**
     * 删除照片
     *
     * @param pictureName 需要删除的照片的主键
     * @param userKey 用户主键
     * @return 是否删除
     */
    int deletePicture(@Param("name") String pictureName, @Param("userKey") int userKey);

    /**
     * 查询所查照片名是否存在
     *
     * @param checkName 所查照片名
     * @param userKey 照片所属用户
     * @return 查询结果
     */
    String queryName(@Param("checkName") String checkName, @Param("userKey") int userKey);

    /**
     *
     * 根据人脸获取与人脸相似的照片
     *
     * @param faceKey 人脸标识符
     * @param userKey 用户
     * @return 结果
     */
    List<Picture> getPictureByUserKeyAndFaceKey(@Param("faceKey") String faceKey, @Param("userKey") int userKey);
}
