package com.simple.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.simple.common.RedisCache;
import com.simple.domian.Health;
import com.simple.domian.Leave;
import com.simple.domian.Student;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@CacheNamespace(implementation= RedisCache.class,eviction=RedisCache.class)
public interface LeaveMapper extends BaseMapper<Leave> {

    IPage<Leave> selectLeaveOpr(IPage<Leave> iPage, @Param(Constants.WRAPPER) Wrapper<Leave> wrapper);
    IPage<Leave> selectLeaveOpr1(IPage<Leave> iPage, @Param(Constants.WRAPPER) Wrapper<Leave> wrapper);

    //根据学生ID获取学生信息
    List<Leave> selectLeaveById(Long id);
    boolean updateLeaveInfo(Leave leave);
}
