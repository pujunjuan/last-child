package com.simple.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.simple.common.RedisCache;
import com.simple.domian.Student;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
@CacheNamespace(implementation= RedisCache.class,eviction=RedisCache.class)
public interface StudentMapper extends BaseMapper<Student> {

    //分页查询学生信息
    IPage<Student> selectByPage(IPage<Student> userPage , @Param(Constants.WRAPPER) Wrapper<Student> Wrapper);
    //分页查询学生信息
    IPage<Student> selectLeaveOpr1(IPage<Student> userPage , @Param(Constants.WRAPPER) Wrapper<Student> Wrapper);
    //获取最新一条数据
    Student getNewStydent();

    //修改学生信息
    int updateStudent(Student student);


    //根据学生ID获取学生信息
    Student selectByIdStudent(Long id);

    Student selectStudentById(Long id);

}
