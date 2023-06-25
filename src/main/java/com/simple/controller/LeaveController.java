package com.simple.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.result.CommonPage;
import com.simple.common.result.Result;
import com.simple.domian.Advice;
import com.simple.domian.Leave;
import com.simple.service.LeaveService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/leave")
public class LeaveController {
    @Autowired
    private LeaveService leaveService;

    //分页返回幼儿请假历史记录
    @GetMapping("/getLeaveHis")
    public Result getLeaveHis(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                 @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                 Leave leave){
        Page<Leave> page=new Page<>(pageNo,pageSize);
        IPage<Leave> leaveIPage=leaveService.getLeaveHis(page,leave);
        CommonPage commonPage=new CommonPage();
        commonPage.setPageNum(pageNo);
        commonPage.setPageSize(pageSize);
        commonPage.setTotal(leaveIPage.getTotal());
        commonPage.setList(leaveIPage.getRecords());
        //封装Result返回
        return Result.success(commonPage);
    }

    /**
     * 分页返回的学生请假信息
     * @param pageNo
     * @param pageSize
     * @param leave
     * @return
     */
    @GetMapping("/getLeaveStaOpr")
    public Result getLeaveStaOpr(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                  Leave leave){
        Page<Leave> page=new Page<>(pageNo,pageSize);
        IPage<Leave> leaveIPage=leaveService.getLeaveStaOpr(page,leave);
        CommonPage commonPage=new CommonPage();
        commonPage.setPageNum(pageNo);
        commonPage.setPageSize(pageSize);
        commonPage.setTotal(leaveIPage.getTotal());
        commonPage.setList(leaveIPage.getRecords());
        //封装Result返回
        return Result.success(commonPage);
    }


    /**
     * 分页返回已处理的请假审批
     * @param pageNo
     * @param pageSize
     * @param leave
     * @return
     */
    @GetMapping("/getLeaveDealOpr")
    public Result getLeaveDealOpr(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                  Leave leave){
        Page<Leave> page=new Page<>(pageNo,pageSize);
        IPage<Leave> leaveIPage=leaveService.getLeaveDealOpr(page,leave);
        CommonPage commonPage=new CommonPage();
        commonPage.setPageNum(pageNo);
        commonPage.setPageSize(pageSize);
        commonPage.setTotal(leaveIPage.getTotal());
        commonPage.setList(leaveIPage.getRecords());
        //封装Result返回
        return Result.success(commonPage);
    }

    /**
     * 分页返回园长审批请假详细信息
     * @param pageNo
     * @param pageSize
     * @param leave
     * @return
     */
    @GetMapping("/getLeaveInfoOpr")
    public Result getLeaveInfoOpr(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                  Leave leave){
        Page<Leave> page=new Page<>(pageNo,pageSize);
        IPage<Leave> leaveIPage=leaveService.getLeaveInfoOpr(page,leave);
        CommonPage commonPage=new CommonPage();
        commonPage.setPageNum(pageNo);
        commonPage.setPageSize(pageSize);
        commonPage.setTotal(leaveIPage.getTotal());
        commonPage.setList(leaveIPage.getRecords());
        //封装Result返回
        return Result.success(commonPage);
    }
    /**
     * 分页返回教师审批请假详细信息
     * @param pageNo
     * @param pageSize
     * @param leave
     * @return
     */
    @GetMapping("/getLeaveInfoOpr1")
    public Result getLeaveInfoOpr1(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                  Leave leave){
        Page<Leave> page=new Page<>(pageNo,pageSize);
        IPage<Leave> leaveIPage=leaveService.getLeaveInfoOpr1(page,leave);
        CommonPage commonPage=new CommonPage();
        commonPage.setPageNum(pageNo);
        commonPage.setPageSize(pageSize);
        commonPage.setTotal(leaveIPage.getTotal());
        commonPage.setList(leaveIPage.getRecords());
        //封装Result返回
        return Result.success(commonPage);
    }

    /**
     * 根据请假id获取请假详情
     */
    @GetMapping("/getLeaveById")
    public Result getLeaveById(@RequestParam Long id){
        QueryWrapper<Leave> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        Leave leave=leaveService.getOne(queryWrapper);
        if(leave!=null){
            return Result.success(leave);
        }
        return Result.error();

    }
    /**
     * 学生提交请假申请
     * @param leave
     * @return
     */
    @PostMapping("/applyLeaveInfo")
    public Result applyLeaveInfo(@RequestBody Leave leave){
        leave.setCreateTime(new Date());
        leave.setStatus("申请中");
        leaveService.save(leave);
        return Result.success("提交成功");
    }

    /**
     * 学生修改请假申请
     * @param leave
     * @return
     */
    @PutMapping("/updateLeaveInfo")
    public Result updateLeaveInfo(@RequestBody Leave leave){
        leaveService.updateLeaveInfo(leave);
        return Result.success("修改成功");
    }

    /**
     *学生撤回请假申请
     * @param id
     * @return
     */
    @PutMapping("/drawLeaveInfo")
    public Result drawLeaveInfo(@RequestParam Long id){
        UpdateWrapper<Leave> queryWrapper=new UpdateWrapper<>();
        queryWrapper.eq("id",id).set("status","撤回").set("limits",0);
        leaveService.update(queryWrapper);
        return Result.success();

    }
    /**
     *教师提交给园长处理请假申请
     * @param id
     * @return
     */
    @PutMapping("/dealLeaveInfo1")
    public Result dealLeaveInfo1(@RequestParam Long id){
        UpdateWrapper<Leave> queryWrapper=new UpdateWrapper<>();
        queryWrapper.eq("id",id).set("limits",1);
        leaveService.update(queryWrapper);
        return Result.success();
    }
    /**
     *教师和老师处理请假申请
     * @param id
     * @param status
     * @return
     */
    @PutMapping("/dealLeaveInfo")
    public Result dealLeaveInfo(@RequestParam Long id,@RequestParam Long sid,@RequestParam String status,@RequestParam String remark){
        UpdateWrapper<Leave> queryWrapper=new UpdateWrapper<>();
        queryWrapper.eq("id",id).set("status",status).set("remark",remark).set("deal_time",new Date()).set("deal_id",sid);
        leaveService.update(queryWrapper);
        return Result.success();
    }

    /**
     * 园长删除请假信息
     * @param id
     * @return
     */
    @DeleteMapping("/delLeaveById")
    public Result dealLeaveInfo(@RequestParam Long id){
        leaveService.removeById(id);
        return Result.success();
    }

    /**
     * 园长批量删除请假信息
     * @param ids
     * @return
     */
    @DeleteMapping("/delLeaveByIds")
    public Result dealLeaveInfo(@RequestBody List<Long> ids){
        leaveService.removeByIds(ids);
        return Result.success();
    }

}
