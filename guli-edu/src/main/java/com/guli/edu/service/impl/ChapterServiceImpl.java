package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.edu.entity.Chapter;
import com.guli.edu.entity.Video;
import com.guli.edu.mapper.ChapterMapper;
import com.guli.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.edu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
@Autowired
   private VideoService videoService;
    @Override
    public List<Map<String, Object>> getListByCourseId(String courseId) {
        //怎么样显示课程大纲列表
        List<Map<String, Object>> mapList = new ArrayList<>();
        //一级：课程章节
        //现根据课程Id获取章节列表
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.orderByAsc("sort");
        List<Chapter> chapterList = baseMapper.selectList(wrapper);

        for (Chapter chapter : chapterList) {
            Map<String, Object> map = new HashMap<>();
            //根据章节的ID查询小节列表
            //二级：课程小节
            QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id", courseId);
            queryWrapper.eq("chapter_id", chapter.getId());
            queryWrapper.orderByAsc("sort");
            List<Video> videoList = videoService.list(queryWrapper);
            map.put("courseId", courseId);
            map.put("chapterId", chapter.getId());
            map.put("title", chapter.getTitle());
            map.put("children", videoList);
            mapList.add(map);
        }
        return mapList;
    }
}
