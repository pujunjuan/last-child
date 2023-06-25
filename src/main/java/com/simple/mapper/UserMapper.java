package com.simple.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.simple.common.RedisCache;
import com.simple.domian.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
@CacheNamespace(implementation=RedisCache.class,eviction=RedisCache.class)
public interface UserMapper extends BaseMapper<User> {
    //获取最新一条数据
    User getNewUser();

    IPage<User> getUserOpr(IPage<User> page, @Param(Constants.WRAPPER) Wrapper<User> wrapper);

}
