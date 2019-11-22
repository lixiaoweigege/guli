package com.guli.edu.controller;


import com.guli.common.entity.Result;
import com.guli.common.entity.ResultCodeEnum;
import com.guli.common.handler.GuliException;
import com.guli.edu.entity.vo.SubjectTreeVo;
import com.guli.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Huaan
 * @since 2019-11-16
 */
@RestController
@RequestMapping("/edu/subject")
@CrossOrigin//跨域注解
@Api(description = "课程分类管理")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    /**
     * 批量导入课程信息（使用excle表格）
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "Excle批量导入")
    @PostMapping("import")
    public Result batchImport(
            @ApiParam(name = "file", value = "Excle文件", required = true)
            @RequestParam("file") MultipartFile file) {
        try {

            List<String> message = subjectService.batacImport(file);
            if (message.size() == 0) {
                return Result.ok().message("批量导入成功");
            } else {
                return Result.error().message("部分导入失败").data("errorMsgList", message);
            }
        } catch (Exception e) {
            throw new GuliException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR);
        }
    }

    @ApiOperation(value = "获取课程分类列表")
    @GetMapping("")
    public Result treeList() {
        List<SubjectTreeVo> treeVos = subjectService.getTreeList();
        return Result.ok().data("rows",treeVos);
    }
}

