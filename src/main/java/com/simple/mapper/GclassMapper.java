package com.simple.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.simple.common.RedisCache;
import com.simple.domian.Gclass;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
@CacheNamespace(implementation= RedisCache.class,eviction=RedisCache.class)
public interface GclassMapper extends BaseMapper<Gclass> {
    /**
     * 分页条件查询
     * @param page
     * @param Wrapper
     * @return
     */
    IPage<Gclass> getGclassByOpr(IPage<Gclass> page,@Param(Constants.WRAPPER) Wrapper<Gclass> Wrapper);

    /**
     * 修改班级信息
     * @param gclass
     * @return
     */
    int updateGclass(Gclass gclass);

    Gclass getGclassById(Long id);
}
