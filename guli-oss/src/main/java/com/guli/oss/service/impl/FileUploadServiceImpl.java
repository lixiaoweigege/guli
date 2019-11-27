package com.guli.oss.service.impl;

import com.aliyun.oss.OSSClient;
import com.guli.oss.service.FileUploadService;
import com.guli.oss.util.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    /**
     * 1、上传文件的内容判断：内容图片的属性；
     * 2、上传图片的解析规则
     */
    //1、怎麽判断上传文件的格式
    static String[] TYPESTR = {".png",".jpg",".gif",".jpeg"};
    /**
     * 上传图片
     * 问题存在：
     * 1、图片格式类型没有校验
     * 2、上传文件内容没有校验
     * @param file
     * @return
     */
    @Override
    public String upload(MultipartFile file) {
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(
                ConstantPropertiesUtil.END_POINT,
                ConstantPropertiesUtil.ACCESS_KEY_ID,
                ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        try {

            Boolean flag = false;
            for(String type : TYPESTR){
                if(StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), type)){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                return "上传文件格式不正确";
            }

            //2、怎么判断文件内容—> 如果我上传的是rm -rf /* 脚本
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image == null){
                return "文件内容不正确";
            } else{
                System.err.println(image.getHeight());
                System.err.println(image.getWidth());
            }
            //先获取文件名称
            String originalFilename = file.getOriginalFilename();
            //获取扩展名 李进.很帅.png
            String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + ext;
            //构建日期路径：avatar/2019/02/26/文件名
            String filePath = new DateTime().toString("yyyy/MM/dd");
            String pathName = ConstantPropertiesUtil.FILE_HOST + "/" + filePath + "/" + fileName;
            // cover/2019/02/26/文件名
            ossClient.putObject(
                    ConstantPropertiesUtil.BUCKET_NAME,
                    pathName,
                    file.getInputStream());
            String url = "https://" + ConstantPropertiesUtil.BUCKET_NAME + "." + ConstantPropertiesUtil.END_POINT + "/" + pathName;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }

        return null;
    }
}
