package com.guli.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.vo.CourseInfo;
import com.guli.edu.entity.vo.CourseQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Huaan
 * @since 2019-11-16
 */
public interface CourseService extends IService<Course> {

    String saveCourseInfo(CourseInfo courseInfo);

    CourseInfo getCourseInfoById(String courseId);

    void updateCourseInfoById(CourseInfo courseInfo);

    void queryCourseByPage(Page<Course> pageParam, CourseQuery query);

    Map<String, Object> getPublishCourseByCourseId(String courseId);

    void updateStatusByCourseId(String courseId);

    List<Course> selectByTeacherId(String id);

    Map<String, Object> pageListWeb(Page<Course> pageParam);

    Map<String, Object> getCourseInfo(String courseId);
}
