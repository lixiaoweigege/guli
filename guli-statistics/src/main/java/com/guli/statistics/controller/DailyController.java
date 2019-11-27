package com.guli.statistics.controller;


import com.guli.common.entity.Result;
import com.guli.statistics.entity.Daily;
import com.guli.statistics.service.DailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author 黎小伟哥哥
 * @since 2019-11-22
 */
@RestController
@RequestMapping("/statistics/daily")
@CrossOrigin
@Api(description = "统计信息管理")
public class DailyController {
    @Autowired
    private DailyService dailyService;

    @GetMapping("1/{date}")
    @ApiOperation(value = "根据日期生成统计信息")
    public Result createStatistics(
            @ApiParam(name = "date", value = "日期格式：xxxx-xx-xx")
            @PathVariable("date") String date) {
        dailyService.createStatistics(date);
        System.out.println("访问成功");
        return Result.ok();
    }
    /**
     * 根据统计的类型和起止时间查询数据
     */
    @GetMapping("showCharts/{type}/{begin}/{end}")
    @ApiOperation(value = "根据统计的类型和起止时间查询数据")
    public Result showCharts(
            @PathVariable("type") String type,
            @PathVariable("begin") String begin,
            @PathVariable("end") String end){
        Map<String, Object> map = dailyService.showCharts(type, begin, end);
        return Result.ok().data(map);
    }
}

