package com.simple.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.simple.domian.Student;
import com.simple.domian.StudentParent;

import java.util.List;

public interface StudentParentService extends IService<StudentParent> {
    IPage<StudentParent> getspOpr(Page<StudentParent> page, StudentParent student);
    List<StudentParent> getStudentParent(Long id);
}
