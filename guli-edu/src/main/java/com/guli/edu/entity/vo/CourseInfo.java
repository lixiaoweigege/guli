package com.guli.edu.entity.vo;

import com.guli.edu.entity.Course;
import com.guli.edu.entity.CourseDescription;
import lombok.Data;

/**
 * 接收前端传递的两个对象的数据
 */
@Data
public class CourseInfo {

    private Course course;

    private CourseDescription courseDescription;

}
