package com.simple.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.result.CommonPage;
import com.simple.common.result.Result;
import com.simple.domian.Parent;
import com.simple.domian.Student;
import com.simple.domian.StudentParent;
import com.simple.service.ParentService;
import com.simple.service.StudentParentService;
import com.simple.service.StudentService;
import javafx.css.ParsedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/parent")
public class ParentController {

    @Autowired
    private ParentService parentService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentParentService studentParentService;
    /**
     * 分页查询所有家长信息
     */
    @GetMapping("/getParentByOpr")
    public Result getParentByOpr(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                 @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
                                 Parent parent){
        //分页信息封装Page对象
        Page<Parent> page = new Page<>(pageNo,pageSize);
        //进行查询
        IPage<Parent> parentIPage = parentService.getParentByOpr(page,parent);
        CommonPage commonPage=new CommonPage();
        commonPage.setPageNum(pageNo);
        commonPage.setPageSize(pageSize);
        commonPage.setTotal(parentIPage.getTotal());
        commonPage.setList(parentIPage.getRecords());
        //封装Result返回
        return Result.success(commonPage);

    }

    /**
     * 获取家长信息
     * @param
     * @return
     */
    @GetMapping("/getParentInfo")
    public Result getParentInfo(){
        return Result.success(parentService.list());

    }

    /**
     * 根据id查询家长信息
     * @param id
     * @return
     */
    @GetMapping("/getParentById")
    public Result getParentById(@RequestParam Long id){
        Parent parent=parentService.getById(id);
        if(!"".equals(parent) && parent!=null){
            return Result.success("查询成功",parent);
        }
        return Result.error("查询失败");

    }

    /**
     * 新增家长信息
     * @param parent
     * @return
     */
    @PostMapping("/insertParent")
    public Result insertParent(@RequestBody Parent parent){
        boolean flag=parentService.save(parent);
        if(flag){
            return Result.success("添加成功");
        }
        return Result.error("添加失败");

    }

    /**
     * 修改家长信息
     * @param parent
     * @return
     */
    @PutMapping("/updateParent")
    public Result updateParent(@RequestBody Parent parent){
        int flag=parentService.updateParent(parent);
        if(flag==1){
            return Result.success("修改成功");
        }
        return Result.error("修改失败");
    }

    /**
     * 根据家长Id删除信息
     * @param id
     * @return
     */
    @DeleteMapping("/delParentById")
    public Result delParentById(@RequestParam("id") Long id){
        Parent parent= parentService.getById(id);
        if(!"".equals(parent)&& null!=parent){

            QueryWrapper<StudentParent> queryWrapper=new QueryWrapper<>();
            queryWrapper.in("parent_id",id);
            studentParentService.remove(queryWrapper);

            parentService.removeById(id);
            return Result.success("删除成功");
        }
        return Result.error("删除失败,请联系管理员");
    }

    /**
     * 根据家长Id批量删除信息
     * @param ids
     * @return
     */
    @DeleteMapping("/delParentByIds")
    public Result delParentByIds(@RequestBody List<Long> ids){
        if(ids.size()>0){
            //删除和家长关联的数据表
            QueryWrapper<StudentParent> queryWrapper1=new QueryWrapper<>();
            queryWrapper1.in("parent_id",ids);
            studentParentService.remove(queryWrapper1);
            parentService.removeBatchByIds(ids);
            return Result.success("删除成功");
        }
        return Result.success("删除失败");
    }

}
