package com.simple.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.simple.domian.Teacher;

public interface TeacherService extends IService<Teacher> {
    IPage<Teacher> getTeacherByOpr(Page<Teacher> page, Teacher teacher);
    int updateTeacher(Teacher teacher);
    Teacher getNewTeacher();
}
