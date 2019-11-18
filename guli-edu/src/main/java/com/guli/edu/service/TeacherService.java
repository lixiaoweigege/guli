package com.guli.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.vo.TeacherQueryVo;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author Huaan
 * @since 2019-11-16
 */
public interface TeacherService extends IService<Teacher> {
    /**
     * 好习惯：
     * 在接口方法上加注释
     * @param pageParam
     * @param vo
     */
    void pageQuery(Page<Teacher> pageParam, TeacherQueryVo vo);
}
