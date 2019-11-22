package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guli.edu.entity.Subject;
import com.guli.edu.entity.vo.SubjectTreeVo;
import com.guli.edu.mapper.SubjectMapper;
import com.guli.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.plugin2.message.Message;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Huaan
 * @since 2019-11-16
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {
    /**
     * 批量导入课程信息
     *
     * @param file
     * @return
     */
    @Override
    @Transactional//插入数据时要开启事务
    public List<String> batacImport(MultipartFile file) {
        List<String> message = new ArrayList<>();
        try {
            //1、获取这个文件流
            InputStream stream = file.getInputStream();
            //2、根据这个流创建workbook这个对象
            HSSFWorkbook workbook = new HSSFWorkbook(stream);
            //3、获取workbook中的第一个Sheet
            HSSFSheet sheet = workbook.getSheetAt(0);
            //4、获取最后一行的索引
            int num = sheet.getLastRowNum();
            //5、判断最后这一行的索引是否为 < 1 ，根本就没有数据
            if (num < 1) {
                message.add("此文件中的数据为空");
                return message;
            }
            //6获取总行数,和分级的数量（分级数即是每行的最大列数）
            int rows = sheet.getPhysicalNumberOfRows();
            int cells = sheet.getRow(0).getPhysicalNumberOfCells();
            //6、如果有数据，那么遍历，从谁开始遍历？从row = 1开始遍历(第一行（row=0)为分级标题
            for (int i = 1; i < rows; i++) {
                //7获取当前行
                HSSFRow row = sheet.getRow(i);
                //定义一个id用于返回前一个单元的id作为当前单元的父id,第一列的父id初始值为0
                String oneId = "0";
                for (int j = 0; j < cells; j++) {
                    //8如果当前单元为空则报错且退出当前循环
                    HSSFCell cell = row.getCell(j);
                    String value;
                    if (cell == null) {
                        message.add("第" + (i + 1) + "行第" + (j + 1) + "列为空");
                        break;
                    }
                    value = cell.getStringCellValue();
                    if (StringUtils.isEmpty(value)) {
                        message.add("第" + (i + 1) + "行第" + (j + 1) + "列数据为空");
                        break;
                    }
                    Subject subject = this.getSubjectByValueAndParentId(value, oneId);
                    if (subject == null) {
                        subject = new Subject();
                        subject.setParentId(oneId);
                        subject.setSort(0);
                        subject.setTitle(value);
                        baseMapper.insert(subject);
                        oneId = subject.getId();
                    } else {
                        oneId = subject.getId();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public List<SubjectTreeVo> getTreeList() {
        List<SubjectTreeVo>  treeVos=new ArrayList<>();
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", 0);
        //根据父级id（“0”）获得一级目录
        List<Subject> list = baseMapper.selectList(wrapper);
        //遍历一级目录中的每一个属性
        for (Subject subject : list) {
            SubjectTreeVo treeVo = new SubjectTreeVo();
            BeanUtils.copyProperties(subject,treeVo);
            QueryWrapper<Subject> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("parent_id",subject.getId());
            //根据该一级目录属性的id获得子目录
            List<Subject> list1 = baseMapper.selectList(wrapper1);
            List<SubjectTreeVo> treeVos1=new ArrayList<>();
            //遍历子目录，将子目录中的属性转换成SubjectTreeVo类型并放入list中
            for (Subject subject1 : list1) {
                SubjectTreeVo treeVo1=new SubjectTreeVo();
                BeanUtils.copyProperties(subject1,treeVo1);
                treeVos1.add(treeVo1);
            }
            //将一级目录属性中的子目录列表放入该一级目录下
            treeVo.setSubjectTreeVos(treeVos1);
            treeVos.add(treeVo);
        }
        return treeVos;
    }

   
    /**
     * 根据parentId和title判断是否存在
     *
     * @param title
     * @param parentId
     * @return
     */
    private Subject getSubjectByValueAndParentId(String title, String parentId) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", title);
        wrapper.eq("parent_id", parentId);
        Subject subject = baseMapper.selectOne(wrapper);
        return subject;
    }
}
