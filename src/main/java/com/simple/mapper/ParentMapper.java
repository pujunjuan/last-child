package com.simple.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.simple.common.RedisCache;
import com.simple.domian.Parent;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
@CacheNamespace(implementation= RedisCache.class,eviction=RedisCache.class)
public interface ParentMapper extends BaseMapper<Parent> {
    /**
     * 分页条件查询
     * @param page
     * @param Wrapper
     * @return
     */
    IPage<Parent> getParentByOpr(IPage<Parent> page, @Param(Constants.WRAPPER) Wrapper<Parent> Wrapper);

    /**
     * 修改家长信息
     * @param parent
     * @return
     */
    int updateParent(Parent parent);

    Parent getParentById(Long id);

}
