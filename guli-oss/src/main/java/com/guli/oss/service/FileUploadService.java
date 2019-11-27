package com.guli.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    /**
     * 上传文件
     * @param file
     * @return 访问URL
     */
    String upload(MultipartFile file);
}
