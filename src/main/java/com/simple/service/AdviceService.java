package com.simple.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.simple.domian.Advice;

public interface AdviceService extends IService<Advice> {
        IPage<Advice> getAdviceOpr(IPage<Advice> page, Advice advice);
}
