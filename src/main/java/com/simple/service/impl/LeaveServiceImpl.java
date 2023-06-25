package com.simple.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.domian.Leave;
import com.simple.mapper.LeaveMapper;
import com.simple.service.LeaveService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LeaveServiceImpl extends ServiceImpl<LeaveMapper, Leave> implements LeaveService {

   //请假次数统计
    public IPage<Leave> getLeaveStaOpr(Page<Leave> page, Leave leave) {
        QueryWrapper<Leave> queryWrapper=new QueryWrapper<>();
        if(leave==null){
            return this.baseMapper.selectLeaveOpr(page,queryWrapper);
        }
        Long applyId=leave.getApplyId();
        Long dealId=leave.getDealId();
        Long classId=leave.getClassId();
        Map<String, Object> time=leave.getParams();
        if(time!=null){
            queryWrapper.apply(StringUtils.checkValNotNull(leave.getParams().get("beginTime")),
                    "date_format (a.start_time,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", leave.getParams().get("beginTime")).
                    apply(StringUtils.checkValNotNull(leave.getParams().get("endTime")),
                            "date_format (a.end_time,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", leave.getParams().get("endTime"));

        }
        if(!"".equals(applyId) && null!=applyId && applyId!=0){
            queryWrapper.eq("a.apply_id",applyId);
        }
        if(!"".equals(dealId) && null!=dealId && dealId!=0){
            queryWrapper.eq("a.deal_id",dealId);
        }
        if(!"".equals(classId) && null!=classId && classId!=0){
            queryWrapper.eq("a.class_id",classId);
        }
        queryWrapper.eq("a.status","同意");
        return this.baseMapper.selectLeaveOpr(page,queryWrapper);
    }

    //分页返回幼儿历史请假记录
    public IPage<Leave> getLeaveHis(Page<Leave> page, Leave leave) {
        QueryWrapper<Leave> queryWrapper=new QueryWrapper<>();
        if(leave==null){
            return this.baseMapper.selectLeaveOpr(page,queryWrapper);
        }
        Long applyId=leave.getApplyId();
        Long dealId=leave.getDealId();
        Long classId=leave.getClassId();
        Map<String, Object> time=leave.getParams();
        if(time!=null){
            queryWrapper.apply(StringUtils.checkValNotNull(leave.getParams().get("beginTime")),
                    "date_format (a.start_time,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", leave.getParams().get("beginTime")).
                    apply(StringUtils.checkValNotNull(leave.getParams().get("endTime")),
                            "date_format (a.end_time,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", leave.getParams().get("endTime"));

        }
        if(!"".equals(applyId) && null!=applyId && applyId!=0){
            queryWrapper.eq("a.apply_id",applyId);
        }
        if(!"".equals(dealId) && null!=dealId && dealId!=0){
            queryWrapper.eq("a.deal_id",dealId);
        }
        if(!"".equals(classId) && null!=classId && classId!=0){
            queryWrapper.eq("b.class_id",classId);
        }
        queryWrapper.ne("a.status","申请中");
        return this.baseMapper.selectLeaveOpr(page,queryWrapper);
    }

    //分页返回已处理的请假审批
    public IPage<Leave> getLeaveDealOpr(Page<Leave> page, Leave leave) {
        QueryWrapper<Leave> queryWrapper=new QueryWrapper<>();
        if(leave==null){
            return this.baseMapper.selectLeaveOpr(page,queryWrapper);
        }
        Long applyId=leave.getApplyId();
        Long dealId=leave.getDealId();
        Long classId=leave.getClassId();
        Map<String, Object> time=leave.getParams();
        if(time!=null){
            queryWrapper.apply(StringUtils.checkValNotNull(leave.getParams().get("beginTime")),
                    "date_format (a.start_time,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", leave.getParams().get("beginTime")).
                    apply(StringUtils.checkValNotNull(leave.getParams().get("endTime")),
                            "date_format (a.end_time,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", leave.getParams().get("endTime"));

        }
        if(!"".equals(applyId) && null!=applyId && applyId!=0){
            queryWrapper.eq("a.apply_id",applyId);
        }
        if(!"".equals(dealId) && null!=dealId && dealId!=0){
            queryWrapper.eq("a.deal_id",dealId);
        }
        if(!"".equals(classId) && null!=classId && classId!=0){
            queryWrapper.eq("b.class_id",classId);
        }
        queryWrapper.ne("a.status","申请中").ne("a.status","撤回");
        return this.baseMapper.selectLeaveOpr(page,queryWrapper);
    }

    //管理员请假审批
    public IPage<Leave> getLeaveInfoOpr(Page<Leave> page, Leave leave) {
        QueryWrapper<Leave> queryWrapper=new QueryWrapper<>();
        queryWrapper.ge("a.end_time-a.start_time",3);
        if(leave==null){
            return this.baseMapper.selectLeaveOpr(page,queryWrapper);
        }
        Long applyId=leave.getApplyId();
        Long dealId=leave.getDealId();
        Map<String, Object> time=leave.getParams();
        if(time!=null){
            queryWrapper.apply(StringUtils.checkValNotNull(leave.getParams().get("beginTime")),
                    "date_format (a.start_time,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", leave.getParams().get("beginTime")).
                    apply(StringUtils.checkValNotNull(leave.getParams().get("endTime")),
                            "date_format (a.end_time,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", leave.getParams().get("endTime"));

        }
        if(!"".equals(applyId) && null!=applyId && applyId!=0){
            queryWrapper.eq("a.apply_id",applyId);
        }
        if(!"".equals(dealId) && null!=dealId && dealId!=0){
            queryWrapper.eq("a.deal_id",dealId);
        }
        queryWrapper.eq("a.status","申请中");
        queryWrapper.or().eq("a.limits",1);
        return this.baseMapper.selectLeaveOpr(page,queryWrapper);
    }

    //教师请假审批
    public IPage<Leave> getLeaveInfoOpr1(Page<Leave> page, Leave leave) {
        QueryWrapper<Leave> queryWrapper=new QueryWrapper<>();
        if(leave==null){
            return this.baseMapper.selectLeaveOpr(page,queryWrapper);
        }
        Long applyId=leave.getApplyId();
        Long dealId=leave.getDealId();
        Long classId=leave.getClassId();
        String status=leave.getStatus();
        Map<String, Object> time=leave.getParams();
        if(time!=null){
            queryWrapper.apply(StringUtils.checkValNotNull(leave.getParams().get("beginTime")),
                    "date_format (a.create_time,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", leave.getParams().get("beginTime")).
                    apply(StringUtils.checkValNotNull(leave.getParams().get("endTime")),
                            "date_format (a.create_time,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", leave.getParams().get("endTime"));

        }
        if(!"".equals(applyId) && null!=applyId && applyId!=0){
            queryWrapper.eq("a.apply_id",applyId);
        }
        if(!"".equals(dealId) && null!=dealId && dealId!=0){
            queryWrapper.eq("a.deal_id",dealId);
        }
        if(!StringUtils.isEmpty(status)){
            queryWrapper.eq("a.status",status);
        }
        if(!"".equals(classId) && null!=classId && classId!=0){
            queryWrapper.eq("b.class_id",classId);
        }
        return this.baseMapper.selectLeaveOpr(page,queryWrapper);
    }

    @Override
    public boolean updateLeaveInfo(Leave leave) {
        return this.baseMapper.updateLeaveInfo(leave);
    }
}
