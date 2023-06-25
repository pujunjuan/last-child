package com.simple.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.result.CommonPage;
import com.simple.common.result.Result;
import com.simple.domian.Advice;
import com.simple.domian.Behavior;
import com.simple.service.BehaviorService;
import com.simple.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("behavior")
public class BehaviorController {

    @Autowired
    private BehaviorService behaviorService;
    @Autowired
    private OssService fileUploadService;
    /**
     * 分页返回日常行为表现（管理员/幼儿）
     * @param pageNo
     * @param pageSize
     * @param behavior
     * @return
     */
    @GetMapping("/getAllBehaviorOpr")
    public Result getAllBehaviorOpr(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                 @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                 Behavior behavior){
        Page<Behavior> page = new Page<>(pageNo,pageSize);
        IPage<Behavior> courseIPage=behaviorService.getBehaviorOpr(page,behavior);
        CommonPage commonPage=new CommonPage();
        commonPage.setPageNum(pageNo);
        commonPage.setPageSize(pageSize);
        commonPage.setTotal(courseIPage.getTotal());
        commonPage.setList(courseIPage.getRecords());
        //封装Result返回
        return Result.success(commonPage);
    }

    /**
     *
     *分页返回自己所管理班级学生的日常行为表现（幼师）
     * @param pageNo
     * @param pageSize
     * @param id
     * @return
     */

    @GetMapping("/getBehaviorOpr")
    public Result getBehaviorOpr(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                 @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                Long id){
        Page<Behavior> page = new Page<>(pageNo,pageSize);
        IPage<Behavior> courseIPage=behaviorService.getBehaviorOpr(page,id);
        CommonPage commonPage=new CommonPage();
        commonPage.setPageNum(pageNo);
        commonPage.setPageSize(pageSize);
        commonPage.setTotal(courseIPage.getTotal());
        commonPage.setList(courseIPage.getRecords());
        //封装Result返回
        return Result.success(commonPage);
    }

    /**
     * 添加学生的日常行为表现
     */
    @PostMapping("/addBehavior")
    public Result addBehavior(@RequestBody Behavior behavior){
        behavior.setTime(new Date());
        boolean flag=behaviorService.save(behavior);
        if(flag){
            return Result.success();
        }
        return Result.error();
    }

    /**
     * 添加学生的日常行为表现
     */
    @PostMapping("/addBehaviorImage")
    public Result addBehaviorImage(@RequestParam Long tid,
                                   @RequestParam String context,
                                   @RequestParam("file") MultipartFile file){
        String photo=fileUploadService.upload(file).getName();
        Behavior behavior=new Behavior();
        behavior.setTid(tid);
        behavior.setContext(context);
        behavior.setTime(new Date());
        behavior.setImage(photo);
        boolean flag=behaviorService.save(behavior);
        if(flag){
            return Result.success();
        }
        return Result.error();
    }

    /**
     * 根据日常行为表现id返回详细信息
     * @param id
     */
    @GetMapping("/getBehaviorById")
    public Result getBehaviorById(@RequestParam Long id){
        Behavior behavior=behaviorService.getById(id);
        if(behavior!=null){
            return Result.success(behavior);
        }
        return Result.error();

    }
//
//    /**
//     * 修改学生日常表现
//     * @param behavior
//     * @return
//     */
//    @PutMapping("/updateBehaviorById")
//    public Result updateBehaviorById(@RequestBody Behavior behavior){
//
//        UpdateWrapper<Behavior> updateWrapper=new UpdateWrapper<>();
//        updateWrapper.set("context",behavior.getContext()).eq("id",behavior.getId());
//        boolean flag=behaviorService.update(updateWrapper);
//        if(flag){
//            return Result.success();
//        }
//        return Result.error();
//    }

    /**
     * 修改学生日常表现
     * @param
     * @return
     */
    @PutMapping("/updateBehaviorById")
    public Result updateBehaviorById(@RequestParam Long id,
                                     @RequestParam String context,
                                     @RequestParam("file") MultipartFile file){
        String photo=fileUploadService.upload(file).getName();
        Behavior behavior=new Behavior();
        behavior.setContext(context);
        behavior.setId(id);
        behavior.setImage(photo);
        UpdateWrapper<Behavior> updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("context",behavior.getContext()).set("image",behavior.getImage()).eq("id",behavior.getId());
        boolean flag=behaviorService.update(updateWrapper);
        if(flag){
            return Result.success();
        }
        return Result.error();
    }

    /**
     * 根据id删除学生日常表现
     * @param id
     * @return
     */
    @DeleteMapping("/delBehaviorById")
    public Result delBehaviorById(@RequestParam Long id){
        boolean flag=behaviorService.removeById(id);
        if(flag){
            return Result.success();
        }
        return Result.error();
    }


    /**
     * 根据id批量删除学生日常表现
     * @param ids
     * @return
     */
    @DeleteMapping("/delBehaviorByIds")
    public Result delBehaviorByIds(@RequestBody List<Long> ids){
        boolean flag=behaviorService.removeByIds(ids);
        if(flag){
            return Result.success();
        }
        return Result.error();
    }



}
