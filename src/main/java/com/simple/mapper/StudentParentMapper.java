package com.simple.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.simple.common.RedisCache;
import com.simple.domian.Advice;
import com.simple.domian.StudentParent;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@CacheNamespace(implementation= RedisCache.class,eviction=RedisCache.class)
public interface StudentParentMapper extends BaseMapper<StudentParent> {
    IPage<StudentParent> getspOpr(IPage<StudentParent> page, @Param(Constants.WRAPPER) Wrapper<StudentParent> wrapper);
    List<StudentParent> getStudentParent(Long id);
}
