package com.simple.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.simple.domian.Leave;

public interface LeaveService extends IService<Leave> {
    IPage<Leave> getLeaveStaOpr(Page<Leave> page, Leave leave);
    IPage<Leave> getLeaveHis(Page<Leave> page, Leave leave);
    IPage<Leave> getLeaveDealOpr(Page<Leave> page, Leave leave);
    IPage<Leave> getLeaveInfoOpr(Page<Leave> page, Leave leave);
    IPage<Leave> getLeaveInfoOpr1(Page<Leave> page, Leave leave);
    boolean updateLeaveInfo(Leave leave);
}
