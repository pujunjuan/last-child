package com.simple.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.result.CommonPage;
import com.simple.common.result.Result;
import com.simple.domian.CourseArrange;
import com.simple.service.CourseArrangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/courseArrange")
public class CourseArrangeController {


    @Autowired
    private CourseArrangeService courseArrangeService;


    /**
     * 获取所有课程安排信息
     * @param pageNo
     * @param pageSize
     * @param courseArrange
     * @return
     */
    @GetMapping("/getcourseArrangeOpr")
    public Result getcourseArrangeOpr(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                      @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                      CourseArrange courseArrange){
        Page<CourseArrange> page = new Page<>(pageNo,pageSize);
        IPage<CourseArrange> courseIPage=courseArrangeService.getCourseArrangeInfo(page,courseArrange);
        CommonPage commonPage=new CommonPage();
        commonPage.setPageNum(pageNo);
        commonPage.setPageSize(pageSize);
        commonPage.setTotal(courseIPage.getTotal());
        commonPage.setList(courseIPage.getRecords());
        //封装Result返回
        return Result.success(commonPage);
    }



    /**
     * 给班级授课添加老师课程安排
     * @return
     */
    @PostMapping("/insertCourseArrange")
    public Result insertCourseArrange(@RequestBody CourseArrange courseArrange){
        QueryWrapper<CourseArrange> teacherCourseQueryWrapper=new QueryWrapper<>();
        //首先查询该是否存在这个班级教授这门课程
        teacherCourseQueryWrapper.eq("teacher_id",courseArrange.getTeacherId()).
                eq("course_id",courseArrange.getCourseId()).
                eq("class_id",courseArrange.getClassId());
        CourseArrange teacherCourse=courseArrangeService.getOne(teacherCourseQueryWrapper);
        //不存在添加数据
        if(!"".equals(teacherCourse) && null!=teacherCourse){
            return Result.error("添加失败");
        }
        CourseArrange teacherCourse1=new CourseArrange();
        teacherCourse1.setCourseId(courseArrange.getCourseId());
        teacherCourse1.setTeacherId(courseArrange.getTeacherId());
        teacherCourse1.setClassId(courseArrange.getClassId());
        teacherCourse1.setTerm(courseArrange.getTerm());
        courseArrangeService.save(teacherCourse1);
        return Result.success("添加成功");
    }

    /**
     * 修改班级授课老师课程安排
     * @param courseArrange,传入的修改值
     * @return
     */
    @PutMapping("/updateCourseArrange")
    public Result updateCourseArrange(@RequestBody CourseArrange courseArrange){
        UpdateWrapper<CourseArrange> teacherCourseQueryWrapper=new UpdateWrapper<>();
        //首先查询该是否存在这个班级教授这门课程
        teacherCourseQueryWrapper.eq("id",courseArrange.getId()).
                set("teacher_id",courseArrange.getTeacherId()).
                set("course_id",courseArrange.getCourseId()).
                set("class_id",courseArrange.getClassId()).
                set("term",courseArrange.getTerm());
        courseArrangeService.update(teacherCourseQueryWrapper);
        return Result.success("修改成功");
    }

    /**
     * 删除班级授课老师课程安排
     * @return
     */
    @DeleteMapping("/delCourseArrange")
    public Result delCourseArrange(@RequestParam Long  id){
        courseArrangeService.removeById(id);
        return Result.success("删除成功");
    }

    /**
     * 批量删除班级授课老师课程安排
     * @return
     */
    @DeleteMapping("/BatchdelCourseArrange")
    public Result BatchdelCourseArrange(@RequestBody List<Long> ids){
        courseArrangeService.removeByIds(ids);
        return Result.success("删除成功");
    }

}
