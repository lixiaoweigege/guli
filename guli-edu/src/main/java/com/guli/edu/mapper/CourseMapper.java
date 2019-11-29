package com.guli.edu.mapper;

import com.guli.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Map;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Huaan
 * @since 2019-11-16
 */
public interface CourseMapper extends BaseMapper<Course> {

    Map<String, Object> getPublishCourseByCourseId(String courseId);
    Map<String,Object> getCourseInfo(String courseId);
}
