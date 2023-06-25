package com.simple.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.result.CommonPage;
import com.simple.common.result.Result;
import com.simple.domian.Student;
import com.simple.domian.StudentParent;
import com.simple.service.StudentParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/stuparent")
public class StudentParentController {

    @Autowired
    private StudentParentService studentParentService;
    /**
     * 分页查询学生
     * @param pageNo
     * @param pageSize
     * @param student
     * @return
     */
    @GetMapping("/getspOpr")
    public Result getspOpr(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                StudentParent student){
        System.out.println(student);
        //分页信息封装Page对象
        Page<StudentParent> page = new Page<>(pageNo,pageSize);
        //进行查询
        IPage<StudentParent> studentIPage = studentParentService.getspOpr(page,student);
        CommonPage commonPage=new CommonPage();
        commonPage.setPageNum(pageNo);
        commonPage.setPageSize(pageSize);
        commonPage.setTotal(studentIPage.getTotal());
        commonPage.setList(studentIPage.getRecords());
        //封装Result返回
        return Result.success(commonPage);
    }

    /**
     * 根据学生id获取家长信息
     * @param id
     * @return
     */
    @GetMapping("/getStudentParent")
    public Result getStudentParent(@RequestParam Long id){
        List<StudentParent> studentParent=studentParentService.getStudentParent(id);
        if(studentParent!=null){
            return Result.success(studentParent);
        }
        return Result.error();

    }

    /**
     * 给学生和家长关系进行关联
     * @param studentParent
     * @return
     */
    @PostMapping("/addStudentParent")
    public Result addStudentParent(@RequestBody StudentParent studentParent){
        //判断该关系是否存在
        QueryWrapper<StudentParent> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("parent_id",studentParent.getParentId())
                .eq("student_id",studentParent.getStudentId())
                .eq("relation",studentParent.getRelation());
        StudentParent flag=studentParentService.getOne(queryWrapper);
        if(flag==null){
            studentParentService.save(studentParent);
            return Result.success("添加成功");
        }
        return Result.error("该关系已存在");
    }

    /**
     * 给学生和家长关系删除关联
     * @param studentParent
     * @return
     */
    @DeleteMapping("/delStudentParent")
    public Result delStudentParent(@RequestBody StudentParent studentParent){
        //判断该关系是否存在
        QueryWrapper<StudentParent> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("parent_id",studentParent.getParentId())
                .eq("student_id",studentParent.getStudentId());
        StudentParent flag=studentParentService.getOne(queryWrapper);
        if(flag!=null){
            studentParentService.remove(queryWrapper);
            return Result.success("删除成功");
        }
        return Result.error("该关系不存在");
    }


}
