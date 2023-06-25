package com.simple.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.simple.domian.Student;

public interface StudentService extends IService<Student> {
    IPage<Student> getStudentByOpr(Page<Student> page, Student student);
    IPage<Student> getLeaveByOpr(Page<Student> page, Student student);
    //修改学生信息
    int updateStudent(Student student);

}
