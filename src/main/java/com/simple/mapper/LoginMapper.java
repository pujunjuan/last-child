package com.simple.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simple.common.RedisCache;
import com.simple.domian.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

@Mapper
//@CacheNamespace(implementation= RedisCache.class,eviction=RedisCache.class)
public interface LoginMapper extends BaseMapper<User> {
    User Login(Long account,String password);
}
