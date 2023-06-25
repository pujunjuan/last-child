package com.simple.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.simple.domian.Behavior;

public interface BehaviorService extends IService<Behavior> {
    IPage<Behavior> getBehaviorOpr(IPage<Behavior> page, Behavior behavior);
    IPage<Behavior> getBehaviorOpr(IPage<Behavior> page, Long  id);
}
