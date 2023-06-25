package com.simple.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.simple.common.RedisCache;
import com.simple.domian.Course;
import com.simple.domian.Teacher;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@CacheNamespace(implementation= RedisCache.class,eviction=RedisCache.class)
public interface CourseMapper extends BaseMapper<Course> {

    IPage<Course> getCourseInfo(IPage<Course> page, @Param(Constants.WRAPPER) Wrapper<Course> Wrapper);

    int updateCourse(Course course);

    List<Teacher> getCourseTeaName(Long id);

    Course selectByIdCourse(Long id);
}
