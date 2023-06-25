package com.simple.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.result.CommonPage;
import com.simple.common.result.Result;
import com.simple.domian.Notice;
import com.simple.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 分页返回系统通知信息
     * @param pageNo
     * @param pageSize
     * @param notice
     * @return
     */
    @GetMapping("/getNoticeOpr")
    public Result getNoticeOpr(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                               @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
                               Notice notice){
        System.out.println(notice);
        //分页信息封装Page对象
        Page<Notice> page = new Page<>(pageNo,pageSize);
        //进行查询
        IPage<Notice> parentIPage = noticeService.getNoticeOpr(page,notice);
        CommonPage commonPage=new CommonPage();
        commonPage.setPageNum(pageNo);
        commonPage.setPageSize(pageSize);
        commonPage.setTotal(parentIPage.getTotal());
        commonPage.setList(parentIPage.getRecords());
        //封装Result返回
        return Result.success(commonPage);

    }

    /**
     * 根据通知id返回详细信息
     * @param id
     */
    @GetMapping("/getNoticeById")
    public Result getNoticeById(@RequestParam Long id){
        Notice notice=noticeService.getById(id);
        if(notice!=null){
            return Result.success(notice);
        }
        return Result.error();

    }

    /**
     * 根据通知最新的一条数据
     */
    @GetMapping("/getNewOneNotice")
    public Result getNewOneNotice(){
        return Result.success(noticeService.getNewOneNotice());
    }


    /**
     * 添加系统通知信息
     * @param notice
     * @return
     */
    @PostMapping("/addNoticeInfo")
    public Result addNoticeInfo(@RequestBody Notice notice){
        notice.setCreateTime(new Date());
        boolean flag=noticeService.save(notice);
        if(flag){
            return Result.success();
        }
        return Result.error();
    }

    /**
     * 修改系统通知信息
     * @param notice
     * @return
     */
    @PutMapping("/updateNoticeInfo")
    public Result updateNoticeInfo(@RequestBody Notice notice){
        boolean flag=noticeService.updateNotice(notice);
        if(flag){
            return Result.success();
        }
        return Result.error();
    }

    /**
     * 根据id删除信息
     * @param id
     * @return
     */
    @DeleteMapping("/delNoticeById")
    public Result delNoticeById(@RequestParam Long id){
        boolean flag=noticeService.removeById(id);
        if(flag){
            return Result.success();
        }
        return Result.error();
    }


    /**
     * 根据id批量删除信息
     * @param ids
     * @return
     */
    @DeleteMapping("/delNoticeByIds")
    public Result delNoticeByIds(@RequestBody List<Long> ids){
        boolean flag=noticeService.removeByIds(ids);
        if(flag){
            return Result.success();
        }
        return Result.error();
    }




}
