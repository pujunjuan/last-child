package com.simple.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.simple.common.RedisCache;
import com.simple.domian.Teacher;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
@CacheNamespace(implementation= RedisCache.class,eviction=RedisCache.class)
public interface TeacherMapper extends BaseMapper<Teacher> {

    //分页查询老师信息
    IPage<Teacher> selectByPage(IPage<Teacher> teacherPage , @Param(Constants.WRAPPER) Wrapper<Teacher> teacherWrapper);

    /**
     * 获取最新一条数据
     * @return
     */
    Teacher getNewTeacher();

    /**
     * 更新老师相关信息
     * @param teacher
     * @return
     */
    int updateTeacher(Teacher teacher);

    Teacher getTeacherById(Long id);



}