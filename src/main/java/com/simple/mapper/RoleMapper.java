package com.simple.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.simple.common.RedisCache;
import com.simple.domian.Role;
import com.simple.domian.Teacher;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
@CacheNamespace(implementation= RedisCache.class,eviction=RedisCache.class)
public interface RoleMapper extends BaseMapper<Role> {

    //分页查询老师信息
    IPage<Role> selectByPage(IPage<Role> tRolePage , @Param(Constants.WRAPPER) Wrapper<Role> RoleWrapper);

    Role getRoleById(Long id);



}
