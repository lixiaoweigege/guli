package com.guli.statistics.service;

import com.guli.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author 黎小伟哥哥
 * @since 2019-11-22
 */
public interface DailyService extends IService<Daily> {

    void createStatistics(String date);

    Map<String, Object> showCharts(String type, String begin, String end);
}
