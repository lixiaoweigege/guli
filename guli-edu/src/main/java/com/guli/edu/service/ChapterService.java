package com.guli.edu.service;

import com.guli.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;

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
public interface ChapterService extends IService<Chapter> {

    List<Map<String, Object>> getListByCourseId(String courseId);
}
