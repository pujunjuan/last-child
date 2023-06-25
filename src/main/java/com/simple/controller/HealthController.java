package com.simple.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.result.CommonPage;
import com.simple.common.result.Result;
import com.simple.domian.Advice;
import com.simple.domian.Health;
import com.simple.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/health")
public class HealthController {
    @Autowired
    private HealthService healthService;

    /**
     * 分页条件查询学生体检情况
     * @param pageNo
     * @param pageSize
     * @param health
     * @return
     */
    @GetMapping("/selectHealthOpr")
    public Result selectHealthOpr(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                  Health health){
        Page<Health> page=new Page<>(pageNo,pageSize);
        IPage<Health> healthIPage=healthService.selectHealthOpr(page,health);
        CommonPage commonPage=new CommonPage();
        commonPage.setPageNum(pageNo);
        commonPage.setPageSize(pageSize);
        commonPage.setTotal(healthIPage.getTotal());
        commonPage.setList(healthIPage.getRecords());
        //封装Result返回
        return Result.success(commonPage);
    }

    /**
     * 根据体检id返回详细信息
     * @param id
     */
    @GetMapping("/getHealthById")
    public Result getHealthById(@RequestParam Long id){
        Health health=healthService.getById(id);
        if(health!=null){
            return Result.success(health);
        }
        return Result.error();

    }

    /**
     * 根据体检id更新学生体检信息
     * @param health
     * @return
     */
    @PutMapping("/updateHealth")
    public Result updateHealth(@RequestBody Health health){
        int i=healthService.updateHealth(health);
        if(i>0){
            return Result.success("修改成功");
        }
        return Result.error("修改失败");
    }


    /**
     * 新增学生体检信息
     * @param health
     * @return
     */
    @PostMapping("/insertHealth")
    public Result insertHealth(@RequestBody Health health){
        health.setTime(new Date());
        boolean flag=healthService.save(health);
        if(flag){
            return Result.success("新增成功");
        }
        return Result.error("新增失败");
    }

    /**
     * 根据体检id删除学生体检信息
     * @param id
     * @return
     */
    @DeleteMapping("delHealthById")
    public Result delHealthById(@RequestParam Long id){
        boolean flag=healthService.removeById(id);
        if(flag){
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    /**
     * 根据体检ids批量删除学生体检信息
     * @param ids
     * @return
     */
    @DeleteMapping("/delHealthByIds")
    public Result delHealthByIds(@RequestBody List<Long> ids){
        boolean flag=healthService.removeBatchByIds(ids);
        if(flag){
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }




}
