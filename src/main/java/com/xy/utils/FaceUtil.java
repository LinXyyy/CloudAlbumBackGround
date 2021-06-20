package com.xy.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author x1yyy
 */
@Component
public class FaceUtil {

    /**
     * 向人脸库中查询人脸,得到一个相似度最高的人脸
     * @param imgPath 照片的保存路径
     * @return 查询结果
     * result:{
     *     "error_code":0,
     *     "error_msg":"SUCCESS",
     *     "log_id":11589654584,
     *     "timestamp":1623924535,
     *     "cached":0,
     *     "result":{
     *         "face_token":"22379c8ca4ca2bd7996d6e635a190812",
     *         "user_list":[
     *             {
     *                 "group_id":"group_repeat",
     *                 "user_id":"ycylp",
     *                 "user_info":"",
     *                 "score":100
     *             }
     *         ]
     *     }
     * }
     */
    public String query(String imgPath) {
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";

        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", Base64Util.getBase64Code(imgPath));
            map.put("group_id_list", "group_repeat");
            map.put("image_type", "BASE64");

            String param = GsonUtils.toJson(map);

            String accessToken = FaceTokenUtil.getAuth();

            JSONObject jsonObject = JSON.parseObject(HttpUtil.post(url, accessToken, "application/json", param));


            JSONObject object = jsonObject.getJSONObject("result");

            if (object == null) {
                return UUID.randomUUID().toString().substring(0, 8);
            }

            JSONObject userList = object.getJSONArray("user_list").getJSONObject(0);
            if (Double.parseDouble(userList.getString("score")) >= 80) {
                return userList.getString("user_id");
            }

            return UUID.randomUUID().toString().substring(0, 8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 向人脸库中添加人脸
     * @param imgPath 照片的存储路径
     * @param faceKey 照片所属人脸的唯一标识符
     * @return 成功或失败
     * result:{
     *     "error_code":0,
     *     "error_msg":"SUCCESS",
     *     "log_id":9400179359975,
     *     "timestamp":1623924096,
     *     "cached":0,
     *     "result":{
     *         "face_token":"46427a8a54132cc98989e286c321b8a4",
     *         "location":{
     *             "left":391.65,
     *             "top":364.08,
     *             "width":390,
     *             "height":393,
     *             "rotation":-3
     *        }
     *     }
     * }
     */
    public int add(String imgPath, String faceKey) {
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";

        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", Base64Util.getBase64Code(imgPath));
            map.put("group_id", "group_repeat");
            map.put("user_id", faceKey);
            map.put("image_type", "BASE64");

            String param = GsonUtils.toJson(map);

            String accessToken = FaceTokenUtil.getAuth();

            JSONObject jsonObject = JSON.parseObject(HttpUtil.post(url, accessToken, "application/json", param));
            return Integer.parseInt(jsonObject.getString("error_code"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int faceDetect(String imgPath) {
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/detect";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", Base64Util.getBase64Code(imgPath));
            map.put("image_type", "BASE64");
            map.put("max_face_num", 120);

            String param = GsonUtils.toJson(map);

            String accessToken = FaceTokenUtil.getAuth();

            String result = HttpUtil.post(url, accessToken, "application/json", param);

            JSONObject jsonObject = JSON.parseObject(result);

            if (Integer.parseInt(jsonObject.getString("error_code")) == 0) {
                JSONObject result1 = jsonObject.getJSONObject("result");
                return (int) result1.get("face_num");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
