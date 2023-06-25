package com.simple.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.result.CommonPage;
import com.simple.common.result.Result;
import com.simple.domian.Course;
import com.simple.domian.CourseArrange;
import com.simple.domian.Teacher;
import com.simple.service.CourseService;
import com.simple.service.CourseArrangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseArrangeService courseArrangeService;

    /**
     * 分页条件查询课程信息
     * @param pageNo
     * @param pageSize
     * @param course
     * @return
     */
    @GetMapping("/getCourseOpr")
    public Result getCourseOpr(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                Course course){
        Page<Course> page = new Page<>(pageNo,pageSize);
        IPage<Course> courseIPage=courseService.getCourseInfo(page,course);
        CommonPage commonPage=new CommonPage();
        commonPage.setPageNum(pageNo);
        commonPage.setPageSize(pageSize);
        commonPage.setTotal(courseIPage.getTotal());
        commonPage.setList(courseIPage.getRecords());
        //封装Result返回
        return Result.success(commonPage);
    }

    /**
     * 获取课程信息
     * @param
     * @return
     */
    @GetMapping("/getCourseInfo")
    public Result getCourseInfo(){
        return Result.success(courseService.list());

    }

    /**
     * 修改课程信息
     * @param course
     * @return
     */
    @PutMapping("/updateCourse")
    public Result updateCourse(@RequestBody Course course){
        courseService.updateCourse(course);
        return Result.success("修改成功");
    }

    /**
     * 新增课程
     * @param course
     * @return
     */
    @PostMapping("/insertCourse")
    public Result insertCourse(@RequestBody Course course){
        boolean flag=courseService.save(course);
        if(flag){
            return Result.success("添加成功");
        }
        return Result.error("添加失败");
    }

    /**
     * 根据课程id删除信息
     * @param id
     * @return
     */
    @DeleteMapping("/delCourseById")
    public Result delCourseById(@RequestParam Long id){
       //判断该课程是否存在
       Course course= courseService.getById(id);
        if(!"".equals(course) && null!=course){
            //删除课程和教师关联的表
            QueryWrapper<CourseArrange> teacherCourseQueryWrapper=new QueryWrapper<>();
            //删除关联数据
            teacherCourseQueryWrapper.eq("course_id",id);
            courseArrangeService.remove(teacherCourseQueryWrapper);
            //删除课程信息
            courseService.removeById(id);
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    /**
     * 根据课程id批量删除信息
     * @param ids
     * @return
     */
    @DeleteMapping("/delCourseByIds")
    public Result delCourseByIds(@RequestBody List<Long> ids){
        //删除课程和教师关联的表
        QueryWrapper<CourseArrange> teacherCourseQueryWrapper=new QueryWrapper<>();
        //删除关联数据
        teacherCourseQueryWrapper.in("course_id",ids);
        courseArrangeService.remove(teacherCourseQueryWrapper);
        //删除课程信息
        courseService.removeBatchByIds(ids);
        return Result.success("删除成功");

    }



}
