package com.simple.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.domian.Behavior;
import com.simple.domian.Gclass;
import com.simple.mapper.BehaviorMapper;
import com.simple.mapper.GclassMapper;
import com.simple.service.BehaviorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BehaviorServiceImpl extends ServiceImpl<BehaviorMapper, Behavior> implements BehaviorService {
@Autowired
    private GclassMapper gclassMapper;

    @Override
    public IPage<Behavior> getBehaviorOpr(IPage<Behavior> page, Behavior behavior) {
        QueryWrapper<Behavior> queryWrapper=new QueryWrapper<>();
        if(behavior==null){
            return this.baseMapper.getBehaviorOpr(page,queryWrapper);
        }
        Long id=behavior.getId();
        Long tid=behavior.getTid();
        if(!"".equals(id) && null!=id && id!=0){
            queryWrapper.eq("id",id);
        }
        if(!"".equals(tid) && null!=tid && tid!=0){
            queryWrapper.eq("t_id",tid);
        }

        return this.baseMapper.getBehaviorOpr(page,queryWrapper);
    }

    public IPage<Behavior> getBehaviorOpr(IPage<Behavior> page, Long id) {
        QueryWrapper<Behavior> queryWrapper=new QueryWrapper<>();
        //根据教师id返回班级id
        QueryWrapper<Gclass> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("t_id",id);
        Gclass gclass=gclassMapper.selectOne(queryWrapper1);
        //根据班级id返回学生信息
        queryWrapper.in("class_id",gclass.getId());
        return this.baseMapper.getBehaviorOpr(page,queryWrapper);
    }
}
