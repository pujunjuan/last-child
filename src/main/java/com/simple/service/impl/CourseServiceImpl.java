package com.simple.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.domian.Course;
import com.simple.domian.Teacher;
import com.simple.mapper.CourseMapper;
import com.simple.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {


    /**
     * 分页条件查询
     * @param page
     * @param course
     * @return
     */
    public IPage<Course> getCourseInfo(Page<Course> page, Course course) {
        QueryWrapper<Course> queryWrapper=new QueryWrapper<>();
        if(course==null){
            return this.baseMapper.getCourseInfo(page,queryWrapper);
        }
        //有条件分页查询语句
        String name = course.getName();
        String type = course.getType();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.likeRight("name",name);
        }
        if(!StringUtils.isEmpty(type)){
            queryWrapper.eq("type",type);
        }
        return this.baseMapper.getCourseInfo(page,queryWrapper);
    }

    /**
     * 更新课程信息
     * @param course
     * @return
     */

    public int updateCourse(Course course) {
        return this.baseMapper.updateCourse(course);
    }

    @Override
    public List<Teacher> getCourseTeaName(Long id) {
        return this.baseMapper.getCourseTeaName(id);
    }


}
