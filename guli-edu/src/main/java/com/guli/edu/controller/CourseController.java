package com.guli.edu.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.entity.Result;
import com.guli.edu.entity.Course;
import com.guli.edu.entity.vo.CourseInfo;
import com.guli.edu.entity.vo.CourseQuery;
import com.guli.edu.service.ChapterService;
import com.guli.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Huaan
 * @since 2019-11-16
 */
@RestController
@RequestMapping("/edu/course")
@CrossOrigin
@Api(description = "课程管理")
public class CourseController {
    @Autowired
    private  CourseService courseService;
    @Autowired
    private ChapterService chapterService;
    @PostMapping("save-course-info")
    @ApiOperation("保存课程基本信息并返回id,用于后面保存章节信息")
    public Result saveCourseInfo(
            @ApiParam(name = "CoureseInfo", value = "包含课程基本信息的表单对象（课程和章节）", required = true)
            @RequestBody CourseInfo courseInfo) {
        String courseId = courseService.saveCourseInfo(courseInfo);
        return Result.ok().data("courseId", courseId);
    }

    /**
     * 根据课程id查询CourseInfo课程信息
     */
    @GetMapping("courseInfoById/{courseId}")
    @ApiOperation("根据课程id查询CourseInfo课程信息")
    public Result courseInfoById(
            @ApiParam(name = "courseId", value = "15", required = true)
            @PathVariable("courseId") String courseId){
        CourseInfo courseInfo = courseService.getCourseInfoById(courseId);
        return Result.ok().data("courseInfo", courseInfo );
    }
    /**
     * 修改课程基本信息
     */
    @PutMapping("update")
    @ApiOperation("修改课程信息")
    public Result updateById(
            @ApiParam(name = "courseInfo",value = "课程对象",required = true)
            @RequestBody CourseInfo courseInfo){
        courseService.updateCourseInfoById(courseInfo);
        return Result.ok();
    }
    /**
     * 分页查询课程信息
     */
    @PostMapping("list/{page}/{limit}")
    @ApiParam("分页查询课程信息-后台显示课程信息使用")
    public Result getList(
            @ApiParam(name = "page",value = "1",required = true)
            @PathVariable("page") Integer page,
            @ApiParam(name = "limit",value = "5",required = true)
            @PathVariable("limit") Integer limit,
            @ApiParam(name = "CourseQuery",value = "CourseQuery",required = false)
            @RequestBody CourseQuery query){
        Page<Course> pageParam = new Page<>(page, limit);
        courseService.queryCourseByPage(pageParam, query);

        List<Course> courseList = pageParam.getRecords();
        long total = pageParam.getTotal();
        return Result.ok().data("total", total).data("rows", courseList);
    }
    /**
     * 根据课程ID获取发布课程的基本信息
     */
    @GetMapping("publish/{courseId}")
    public Result getPublishCourseByCourseId(@PathVariable("courseId") String courseId){
        Map<String, Object> map = courseService.getPublishCourseByCourseId(courseId);
        return Result.ok().data(map);
    }

    /**
     * 根据课程Id修改课程状态
     */
    @GetMapping("publishStatusByCourseId/{courseId}")
    public Result publishStatusByCourseId(@PathVariable("courseId") String courseId){
        courseService.updateStatusByCourseId(courseId);
        return Result.ok();
    }
    @ApiOperation(value = "分页课程列表-前台显示课程信息使用")
    @GetMapping(value = "{page}/{limit}")
    public Result pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit){

        Page<Course> pageParam = new Page<Course>(page, limit);

        Map<String, Object> map = courseService.pageListWeb(pageParam);

        return  Result.ok().data(map);
    }
    /**
     * 根据课程Id获取课程详情数据
     */
    @GetMapping("getCourseInfo/{courseId}")
    public Result getCourseInfo(@PathVariable("courseId") String courseId){
        //1、课程基本信息：course表，讲师的信息，课程分类的信息
        Map<String, Object> map = courseService.getCourseInfo(courseId);
        //2、大纲：根据课程Id获取大纲列表
        List<Map<String, Object>> mapList = chapterService.getListByCourseId(courseId);
        return Result.ok()
                .data("courseInfo", map)
                .data("chapterList", mapList);
    }
}

