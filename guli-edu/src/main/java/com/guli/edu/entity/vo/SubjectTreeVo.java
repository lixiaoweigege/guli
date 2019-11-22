package com.guli.edu.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class SubjectTreeVo {
    public String id;
    public String title;
    List<SubjectTreeVo> subjectTreeVos=new ArrayList<>();

}
