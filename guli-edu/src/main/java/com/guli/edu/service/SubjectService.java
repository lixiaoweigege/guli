package com.guli.edu.service;

import com.guli.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.vo.SubjectTreeVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Huaan
 * @since 2019-11-16
 */
public interface SubjectService extends IService<Subject> {

    List<String> batacImport(MultipartFile file);

    List<SubjectTreeVo> getTreeList();
}
