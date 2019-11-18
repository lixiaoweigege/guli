package com.guli.edu.entity.vo;

import lombok.Data;

@Data
public class TeacherQueryVo {

    //讲师名称
    private String name;
    //讲师等级
    private Integer level;
    //起始时间
    private String begin;
    //截止时间
    private String end;


}
