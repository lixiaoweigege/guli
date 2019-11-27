package com.guli.ucenter.controller;


import com.guli.common.entity.Result;
import com.guli.ucenter.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author 黎小伟哥哥
 * @since 2019-11-22
 */
@Api(description = "管理用户信息")
@RestController
@RequestMapping("/ucenter/member")
@CrossOrigin
public class MemberController {
    @Autowired
    private MemberService memberService;

    //根据日期查询当天用户注册数量
    @ApiOperation(value = "根据时间获取当天用户注册个数")
    @GetMapping("registerCount/{date}")
    public Result getRegisterCount(
            @ApiParam(name = "date", value = "2019-01-02", required = true)
            @PathVariable("date") String date
            ) {
        Integer count=memberService.getRegisterCount(date);
        return Result.ok().data("registerCount",count);
    }
}

