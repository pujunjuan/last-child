package com.simple.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.domian.CourseArrange;
import com.simple.mapper.CourseArrangeMapper;
import com.simple.service.CourseArrangeService;
import org.springframework.stereotype.Service;

@Service
public class CourseArrangeServiceImpl extends ServiceImpl<CourseArrangeMapper, CourseArrange> implements CourseArrangeService {


    public IPage<CourseArrange> getCourseArrangeInfo(Page<CourseArrange> page, CourseArrange courseArrange) {
        QueryWrapper<CourseArrange> queryWrapper = new QueryWrapper<>();
        if (courseArrange == null) {
            return this.baseMapper.getCourseArrange(page, queryWrapper);
        }
        Long classId=courseArrange.getClassId();
        Long teacherId=courseArrange.getTeacherId();
        Long courseId=courseArrange.getCourseId();
        if(!"".equals(classId)&& null!=classId && classId!=0){
            queryWrapper.in("class_id",classId);
        }
        if(!"".equals(teacherId)&& null!=teacherId && teacherId!=0){
            queryWrapper.in("teacher_id",teacherId);
        }
        if(!"".equals(courseId)&& null!=courseId && courseId!=0){
            queryWrapper.in("course_id",courseId);
        }
        return  this.baseMapper.getCourseArrange(page,queryWrapper);
    }
}
