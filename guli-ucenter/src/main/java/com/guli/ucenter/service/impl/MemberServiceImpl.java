package com.guli.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.ucenter.entity.Member;
import com.guli.ucenter.mapper.MemberMapper;
import com.guli.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author 黎小伟哥哥
 * @since 2019-11-22
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Override
    public Integer getRegisterCount(String date) {
        QueryWrapper<Member> wrapper=new QueryWrapper<>();
        wrapper.likeRight("gmt_create",date);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }
}
