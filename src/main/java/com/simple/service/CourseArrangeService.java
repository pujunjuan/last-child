package com.simple.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.simple.domian.CourseArrange;


public interface CourseArrangeService extends IService<CourseArrange> {
    IPage<CourseArrange> getCourseArrangeInfo(Page<CourseArrange> page, CourseArrange courseArrange);
}
