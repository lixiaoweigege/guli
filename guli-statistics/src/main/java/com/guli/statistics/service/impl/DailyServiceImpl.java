package com.guli.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.common.entity.Result;
import com.guli.statistics.client.UcenterRemoteService;
import com.guli.statistics.entity.Daily;
import com.guli.statistics.mapper.DailyMapper;
import com.guli.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author 黎小伟哥哥
 * @since 2019-11-22
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {
    @Autowired
    UcenterRemoteService ucenterRemoteService;

    @Override
    public void createStatistics(String date) {
        //统计的时间只能是一天只要一个不能统计的时间有多个；
        //根据统计的这个时间查询统计中是否存在如果存在直接删除；
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", date);
        baseMapper.delete(wrapper);
        //数据存放在result对象的data中
        //获取统计数据
        //用户注册人数
        Integer registerNum = (Integer) ucenterRemoteService.getRegisterCount(date).getData().get("registerCount");
        //因为还没有完成登录监测功能，所以先使用随机数代替
        Integer loginNum = RandomUtils.nextInt(100, 200);
        //数量播放量
        Integer videoNum = RandomUtils.nextInt(100, 200);
        //课程增量
        Integer courseNum = RandomUtils.nextInt(100, 200);
        //插入数据
        Daily daily = new Daily();
        daily.setDateCalculated(date);
        daily.setRegisterNum(registerNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoNum);
        daily.setCourseNum(courseNum);
        daily.setGmtCreate(new Date());
        daily.setGmtModified(new Date());
        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> showCharts(String type, String begin, String end) {
        //1、根据类型查询和时间所有的数据
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.select("date_calculated", type);
        wrapper.between("date_calculated", begin, end);
        List<Daily> dailyList = baseMapper.selectList(wrapper);
        List<String> dateList = new ArrayList<>();
        List<Integer> dataLsit = new ArrayList<>();
        for (Daily daily : dailyList) {
            dateList.add(daily.getDateCalculated());
            switch (type) {
                case "register_num":
                    dataLsit.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    dataLsit.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    dataLsit.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataLsit.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        //把集合放Map中
        Map<String, Object> map = new HashMap<>();
        map.put("dateList", dateList);
        map.put("dataList", dataLsit);

        return map;
    }


}
