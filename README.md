# 云端智能相册

## 背景

 随着手机像素越来越高，对于手机可以换但是照片不能丢的你来说，手机容量是不是越来越不够用了？云相册可以很好的解决这个棘手的问题，但是相册只有存储功能是远远不够的。基于云上的大量计算资源，我们可以让相册越来越智能。

## Tip

该仓库只保存后端接口代码，前端已经在创建文件夹了

## 项目配置

- Spring + SpringMVC + Mybatis
- Maven
- Mysql
- 腾讯云图片分析SDK——获得图片的所属分类
- 百度云人脸识别SDK——用于分析人脸的匹配度

## 更新日志

### V 1.0

- 实现照片批量上传，下载，删除
- 上传的照片可自动分类
- 用户的登录校验，注册

### V 2.0

- 增加人脸检索功能
  - 用户上传一张人脸照片可以检索到此用户下所有符合该人脸特征的照片

### V 2.1

- 优化各个接口的返回值，可以更加清晰的识别响应结果

### V 2.2

-  增加精彩时刻自动剪辑功能，目前可剪辑如下内容
  - 那年今日
  - ***年，沿途的风景

## 联系方式

- QQ：1508300855