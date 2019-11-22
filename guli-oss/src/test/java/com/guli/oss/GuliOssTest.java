package com.guli.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GuliOssTest {
    @Test
    public void OssTest() throws FileNotFoundException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4Ftuz3GWDaf6AYFZMUvP";
        String accessKeySecret = "hll1oQUbK1mWa8fQpflOjnICDoV81Q";
        String bucketName = "guli-weige";

// 创建OSSClient实例。
        OSS ossClient = new OSSClient(endpoint,accessKeyId,accessKeySecret);

// 上传文件流。
        File file= new File("C:\\Users\\23132\\Desktop\\test.jpg");
        InputStream inputStream = new FileInputStream(file);
        PutObjectResult result = ossClient.putObject(bucketName, "mytest1.jpg", inputStream);
        //如果result.getResponse()为null则说明上传成功
        System.out.println( result.getResponse());
// 关闭OSSClient。
        ossClient.shutdown();
    }
}
