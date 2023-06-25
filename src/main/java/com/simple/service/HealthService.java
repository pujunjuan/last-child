package com.simple.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.simple.domian.Health;

public interface HealthService extends IService<Health> {
    IPage<Health> selectHealthOpr(Page<Health> page,Health health);
    int updateHealth(Health health);
}
