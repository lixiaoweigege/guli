package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.Course;
import com.guli.edu.entity.CourseDescription;
import com.guli.edu.entity.vo.CourseInfo;
import com.guli.edu.entity.vo.CourseQuery;
import com.guli.edu.mapper.CourseMapper;
import com.guli.edu.service.CourseDescriptionService;
import com.guli.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Huaan
 * @since 2019-11-16
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Override
    @Transactional
    public String saveCourseInfo(CourseInfo courseInfo) {
        //从courseInfo中获得course对象并插入course表中
        baseMapper.insert(courseInfo.getCourse());
        String courseId = courseInfo.getCourse().getId();
        //吧course返回的id作为CourseDescription的id，前提CourseDescription的id已经设置为手动输入
        courseInfo.getCourseDescription().setId(courseId);
        courseDescriptionService.save(courseInfo.getCourseDescription());
        return courseId;
    }

    @Override
    public CourseInfo getCourseInfoById(String courseId) {
        CourseInfo courseInfo = new CourseInfo();
        Course course = baseMapper.selectById(courseId);
        courseInfo.setCourse(course);
        //查询课程描述
        CourseDescription description = courseDescriptionService.getById(courseId);
        courseInfo.setCourseDescription(description);
        return courseInfo;
    }

    @Override
    public void updateCourseInfoById(CourseInfo courseInfo) {
        baseMapper.updateById(courseInfo.getCourse());
        courseInfo.getCourseDescription().setId(courseInfo.getCourse().getId());
        courseDescriptionService.updateById(courseInfo.getCourseDescription());
    }

    @Override
    public void queryCourseByPage(Page<Course> pageParam, CourseQuery query) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        if (query == null) {
            baseMapper.selectPage(pageParam, wrapper);
            return;
        }
        String title = query.getTitle();
        String teacherId = query.getTeacherId();
        String subjectParentId = query.getSubjectParentId();
        String subjectId = query.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            wrapper.eq("teacher_id", teacherId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id", subjectId);
        }
        baseMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public Map<String, Object> getPublishCourseByCourseId(String courseId) {
        Map<String, Object> map = baseMapper.getPublishCourseByCourseId(courseId);
        return map;
    }

    @Override
    public void updateStatusByCourseId(String courseId) {
        Course course = baseMapper.selectById(courseId);
        course.setStatus("Normal");
        baseMapper.updateById(course);
    }

    @Override
    public List<Course> selectByTeacherId(String id) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<Course>();

        queryWrapper.eq("teacher_id", id);
        //按照最后更新时间倒序排列
        queryWrapper.orderByDesc("gmt_modified");

        List<Course> courses = baseMapper.selectList(queryWrapper);
        return courses;
    }

    @Override
    public Map<String, Object> pageListWeb(Page<Course> pageParam) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_modified");
        baseMapper.selectPage(pageParam, queryWrapper);
        List<Course> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }
}
