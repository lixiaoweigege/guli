package com.guli.edu.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.entity.Result;
import com.guli.common.handler.GuliException;
import com.guli.edu.entity.Course;
import com.guli.edu.entity.Teacher;
import com.guli.edu.entity.vo.TeacherQueryVo;
import com.guli.edu.service.CourseService;
import com.guli.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Huaan
 * @since 2019-11-16
 */
@Api(description = "教师管理")
@CrossOrigin
@RestController
@RequestMapping("/edu/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;

    /**
     * 讲师列表查询
     */
    @GetMapping("list")
    @ApiOperation("获取所有教师信息")
    public Result list() {
        List<Teacher> list = teacherService.list(null);
        return Result.ok().data("rows", list);
    }

    @DeleteMapping("delete/{id}")
    @ApiOperation("根据id删除教师")
    public Result removeById(
            @ApiParam(name = "id", value = "讲师id", required = true)
            @PathVariable String id) {
        teacherService.removeById(id);
        return Result.ok();
    }


    @ApiOperation(value = "讲师分页查询带条件")
    @PostMapping("{page}/{limit}")
    public Result teacherPageByQuery(
            @ApiParam(name = "page", value = "当前页数")
            @PathVariable("page") Integer page,
            @ApiParam(name = "limit", value = "每页显示记录数")
            @PathVariable("limit") Integer limit,
            @ApiParam(name = "vo", value = "查询的条件", required = false)
            @RequestBody TeacherQueryVo vo) {
        //定义Page对象
        Page<Teacher> pageParam = new Page<>(page, limit);
        //条件Service条件分页查询方法
        teacherService.pageQuery(pageParam, vo);
        //和上面分页查询的返回数据一模一样
        return Result.ok()
                .data("total", pageParam.getTotal())
                .data("rows", pageParam.getRecords());
    }

    /**
     * 讲师新增
     */
    @ApiOperation(value = "讲师新增")
    @PostMapping("save")
    public Result saveTeacher(@RequestBody Teacher teacher) {
        teacherService.save(teacher);
        return Result.ok();
    }

    /**
     * 根据讲师id查询讲师信息
     */
    @ApiOperation(value = "根据ID查询讲师信息")
    @GetMapping("{id}")
    public Result teacherById(@PathVariable("id") String id) {
        Teacher teacher = teacherService.getById(id);
        //根据讲师id查询这个讲师的课程列表
        List<Course> courseList = courseService.selectByTeacherId(id);
        return Result.ok().data("teacher", teacher).data("courseList", courseList);
    }

    @ApiOperation(value = "讲师修改")
    @PutMapping("update")
    public Result updateById(@RequestBody Teacher teacher) {
        teacherService.updateById(teacher);
        return Result.ok();
    }

    /***
     * 前台方法
     */
    @ApiOperation(value = "分页讲师列表不带条件")
    @GetMapping(value = "{page}/{limit}")
    public Result pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {

        Page<Teacher> pageParam = new Page<Teacher>(page, limit);

        Map<String, Object> map = teacherService.pageListWeb(pageParam);

        return Result.ok().data(map);
    }
}

