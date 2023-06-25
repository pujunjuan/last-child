package com.simple.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.domian.Teacher;
import com.simple.mapper.ParentMapper;
import com.simple.domian.Parent;
import com.simple.service.ParentService;
import org.springframework.stereotype.Service;

@Service
public class ParentServiceImpl extends ServiceImpl<ParentMapper,Parent> implements ParentService {

    @Override
    public IPage<Parent> getParentByOpr(Page<Parent> page, Parent parent) {
        QueryWrapper<Parent> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().orderByDesc(Parent::getId);
        //无条件分页查询语句
        if(parent==null){
            return this.baseMapper.getParentByOpr(page,queryWrapper);
        }
        //有条件分页查询语句
        String name = parent.getName();
        String card = parent.getCard();
        String phone= parent.getTelephone();
        String sex = parent.getSex();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.likeRight("name",name);
        }
        if(!StringUtils.isEmpty(card)){
            queryWrapper.eq("card",card);
        }
        if(!StringUtils.isEmpty(phone)){
            queryWrapper.eq("telephone",phone);
        }
        if(!StringUtils.isEmpty(sex)){
            queryWrapper.eq("sex",sex);
        }
        queryWrapper.eq("deleted",0);
        return this.baseMapper.getParentByOpr(page,queryWrapper);
    }

    /**
     * 修改家长信息
     * @param parent
     * @return
     */
    @Override
    public int updateParent(Parent parent) {
        return this.baseMapper.updateParent(parent);
    }
}
