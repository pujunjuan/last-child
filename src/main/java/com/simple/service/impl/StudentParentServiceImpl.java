package com.simple.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.domian.Student;
import com.simple.domian.StudentParent;
import com.simple.mapper.StudentParentMapper;
import com.simple.service.StudentParentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentParentServiceImpl extends ServiceImpl<StudentParentMapper, StudentParent> implements StudentParentService {

    @Override
    public IPage<StudentParent> getspOpr(Page<StudentParent> page, StudentParent student) {
        QueryWrapper<StudentParent> queryWrapper =  new QueryWrapper<>();
        //无条件查询
        if(student==null){
            return this.baseMapper.getspOpr(page,queryWrapper);
        }
        //有条件查询，构建条件参数

        Long studentId = student.getStudentId();
        Long parentId= student.getParentId();
        Long classId= student.getStudent().getClassId();
        if(!"".equals(studentId) && null != studentId && studentId !=0){
            queryWrapper.eq("b.student_id",studentId);
        }
        if(!"".equals(parentId)&&  null!=parentId && parentId!=0){
            queryWrapper.eq("b.parent_id",parentId);
        }
        if(!"".equals(classId)&&  null!=classId && classId!=0){
            queryWrapper.eq("a.class_id",classId);
        }
        queryWrapper.eq("a.deleted",0);
        IPage<StudentParent> StudentPage = this.baseMapper.getspOpr(page,queryWrapper);
        return  StudentPage;
    }

    public List<StudentParent> getStudentParent(Long id) {
        return this.baseMapper.getStudentParent(id);
    }
}
