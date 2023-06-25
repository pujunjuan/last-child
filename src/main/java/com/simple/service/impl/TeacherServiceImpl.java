package com.simple.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.domian.User;
import com.simple.mapper.TeacherMapper;
import com.simple.domian.Teacher;
import com.simple.service.TeacherService;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {


    @Override
    public IPage<Teacher> getTeacherByOpr(Page<Teacher> page, Teacher teacher) {
        // 1 排序：按照sort字段排序
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByDesc(Teacher::getId);
        // 2 有分页查询 无条件查询
        if(teacher==null){
            return this.baseMapper.selectByPage(page,queryWrapper);
        }
        // 3 构造条件查询
        String name = teacher.getName();
        Long sno = teacher.getId();
        String  phone= teacher.getTelephone();
        String sex = teacher.getSex();
        String card=teacher.getCard();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.likeRight("name", name);
        }
        if (!"".equals(sno) && sno != null && sno!=0) {
            queryWrapper.eq("id", sno);
        }
        if (!"".equals(card) && card != null) {
            queryWrapper.eq("card", card);
        }
        if (!StringUtils.isEmpty(phone)) {
            queryWrapper.ge("telephone", phone);
        }
        if (!StringUtils.isEmpty(sex)) {
            queryWrapper.le("sex", sex);
        }
        queryWrapper.eq("deleted",0);
        //4、返回结果
        return this.baseMapper.selectByPage(page,queryWrapper);
    }

    @Override
    public int updateTeacher(Teacher teacher) {
        return this.baseMapper.updateTeacher(teacher);
    }

    @Override
    public Teacher getNewTeacher() {
        return this.baseMapper.getNewTeacher();
    }

}
