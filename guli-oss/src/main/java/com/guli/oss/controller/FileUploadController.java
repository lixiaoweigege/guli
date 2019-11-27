package com.guli.oss.controller;

import com.guli.common.entity.Result;
import com.guli.oss.service.FileUploadService;
import com.guli.oss.util.ConstantPropertiesUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("oss")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("file/upload")
    public Result upload(MultipartFile file,
                         @ApiParam(name = "host", value = "上传目录", required = false)
                         String host){
        if(!StringUtils.isEmpty(host)){
            ConstantPropertiesUtil.FILE_HOST = host;
        }
        String url = fileUploadService.upload(file);
        return Result.ok().data("url", url);
    }

}
