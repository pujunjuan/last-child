package com.simple.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.simple.domian.Course;
import com.simple.domian.Teacher;

import java.util.List;

public interface CourseService extends IService<Course> {
    IPage<Course> getCourseInfo(Page<Course> page, Course course);

    int updateCourse(Course course);

    List<Teacher> getCourseTeaName(Long id);
}
