package com.simple.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.result.CommonPage;
import com.simple.common.result.Result;
import com.simple.domian.Advice;
import com.simple.domian.Behavior;
import com.simple.domian.Notice;
import com.simple.service.AdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/advice")
public class AdviceController {
    @Autowired
    private AdviceService adviceService;

    /**
     * 分页返回意见反馈
     * @param pageNo
     * @param pageSize
     * @param advice
     * @return
     */
    @GetMapping("/getAdviceOpr")
    public Result getAdviceOpr(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                    @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                    Advice advice){
        Page<Advice> page = new Page<>(pageNo,pageSize);
        IPage<Advice> courseIPage=adviceService.getAdviceOpr(page,advice);
        CommonPage commonPage=new CommonPage();
        commonPage.setPageNum(pageNo);
        commonPage.setPageSize(pageSize);
        commonPage.setTotal(courseIPage.getTotal());
        commonPage.setList(courseIPage.getRecords());
        //封装Result返回
        return Result.success(commonPage);
    }
    /**
     * 根据通知id返回详细信息
     * @param id
     */
    @GetMapping("/getAdviceById")
    public Result getAdviceById(@RequestParam Long id){
        Advice advice=adviceService.getById(id);
        if(advice!=null){
            return Result.success(advice);
        }
        return Result.error();

    }

    /**
     * 添加意见反馈
     */
    @PostMapping("/addAdvice")
    public Result addAdvice(@RequestBody Advice advice){
        advice.setCreateTime(new Date());
        boolean flag=adviceService.save(advice);
        if(flag){
            return Result.success();
        }
        return Result.error();
    }


    /**
     * 意见反馈进行回复
     * @param id
     * @return
     */
    @PutMapping("/updateAdviceById")
    public Result updateAdviceById(@RequestParam Long id,@RequestParam String reply){
        UpdateWrapper<Advice> updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("reply",reply).set("status",2).eq("id",id);
        boolean flag=adviceService.update(updateWrapper);
        if(flag){
            return Result.success();
        }
        return Result.error();
    }

    /**
     * 根据id删除意见反馈
     * @param id
     * @return
     */
    @DeleteMapping("/delAdviceById")
    public Result delAdviceById(@RequestParam Long id){
        boolean flag=adviceService.removeById(id);
        if(flag){
            return Result.success();
        }
        return Result.error();
    }


    /**
     * 根据id批量删除意见反馈
     * @param ids
     * @return
     */
    @DeleteMapping("/delAdviceByIds")
    public Result delBehaviorByIds(@RequestBody List<Long> ids){
        boolean flag=adviceService.removeByIds(ids);
        if(flag){
            return Result.success();
        }
        return Result.error();
    }

}
