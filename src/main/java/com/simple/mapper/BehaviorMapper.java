package com.simple.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.simple.common.RedisCache;
import com.simple.domian.Behavior;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
@CacheNamespace(implementation= RedisCache.class,eviction=RedisCache.class)
public interface BehaviorMapper extends BaseMapper<Behavior> {
    IPage<Behavior> getBehaviorOpr(IPage<Behavior> page, @Param(Constants.WRAPPER) Wrapper<Behavior> wrapper);
}
