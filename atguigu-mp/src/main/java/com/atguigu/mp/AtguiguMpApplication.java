package com.atguigu.mp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.atguigu.mp.mapper")
public class AtguiguMpApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtguiguMpApplication.class, args);
    }

}
