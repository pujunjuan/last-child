package com.simple.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.domian.Health;
import com.simple.domian.Teacher;
import com.simple.mapper.HealthMapper;
import com.simple.service.HealthService;
import org.springframework.stereotype.Service;

@Service
public class HealthServiceImpl extends ServiceImpl<HealthMapper, Health> implements HealthService {
    @Override
    public IPage<Health> selectHealthOpr(Page<Health> page, Health health) {
        QueryWrapper<Health> queryWrapper=new QueryWrapper<>();
        if(null==health){
            return this.baseMapper.selectHealthOpr(page,queryWrapper);
        }
        Long sid=health.getSid();
        Long classId=health.getClassId();
        if(!"".equals(sid) && null != sid && sid !=0){
            queryWrapper.eq("s_id",sid);
        }
        if(!"".equals(classId) && null != classId && classId !=0){
            queryWrapper.eq("b.class_id",classId);
        }
        return this.baseMapper.selectHealthOpr(page,queryWrapper);
    }

    @Override
    public int updateHealth(Health health) {
        return this.baseMapper.updateHealth(health);
    }
}
