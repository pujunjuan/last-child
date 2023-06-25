package com.simple.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.result.CommonPage;
import com.simple.common.result.Result;
import com.simple.domian.Gclass;
import com.simple.domian.Student;
import com.simple.service.GclassService;
import com.simple.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/class")
public class GclassController {
    @Autowired
    private GclassService gclassService;

    @Autowired
    private StudentService studentService;

    /**
     * 返回班级信息
     * @param
     * @return
     */
    @GetMapping("/getClassInfo")
    public Result getClassInfo(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                               @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
                               Gclass gclass){
        //分页信息封装Page对象
        Page<Gclass> page = new Page<>(pageNo,pageSize);
        //进行查询
        IPage<Gclass> parentIPage = gclassService.getGclassByOpr(page,gclass);
        System.out.println(parentIPage);
        CommonPage commonPage=new CommonPage();
        commonPage.setPageNum(pageNo);
        commonPage.setPageSize(pageSize);
        commonPage.setTotal(parentIPage.getTotal());
        commonPage.setList(parentIPage.getRecords());
        //封装Result返回
        return Result.success(commonPage);

    }

    /**
     * 根据老师id获取班级id
     * @param id
     * @return
     */
    @GetMapping("/getClassStudent")
    public Result getClassStudent(@RequestParam Long id){
        //根据教师id获取班级id
        QueryWrapper<Gclass> gclassQueryWrapper=new QueryWrapper<>();
        gclassQueryWrapper.eq("t_id",id);
        Gclass gclass=gclassService.getOne(gclassQueryWrapper);
        return Result.success(gclass.getId());
//        //获取班级id后，查询学生相关信息
//        QueryWrapper<Student> studentQueryWrapper=new QueryWrapper<>();
//        studentQueryWrapper.in("class_id",gclass.getId());
//        List<Student> list=studentService.list(studentQueryWrapper);
            //分页信息封装Page对象

    }


    /**
     * 获取班级信息
     * @param
     * @return
     */
    @GetMapping("/getGclassInfo")
    public Result getGclassInfo(){
        return Result.success(gclassService.list());

    }

    /**
     * 根据班级ID搜索班级信息
     * @param id
     * @return
     */
    @GetMapping("/getClassById/{id}")
    public Result getClassById(@PathVariable("id") Long id){

        Gclass gclass=gclassService.getById(id);
        if(!"".equals(gclass)&& null!= gclass){
            return Result.success("查询成功",gclass);
        }
        return Result.error("班级不存在，请联系管理员");
    }

    /**
     * //班级名称查询每个班级有哪些学生
     * @param name
     * @return
     */
    @GetMapping("/getClassName")
    public Result getClassName(@RequestParam String name){
        QueryWrapper<Gclass> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",name);
        Gclass gclass=gclassService.getOne(queryWrapper);
        QueryWrapper<Student> studentQueryWrapper=new QueryWrapper<>();
        Long id=gclass.getId();
        studentQueryWrapper.eq("class_id",id);
        //返回班级人数数量
        Long num=studentService.count(studentQueryWrapper);
        List<Student> list = studentService.list(studentQueryWrapper);
        if(list != null){
            return Result.success("查询成功",list);
        }
        return Result.error("查询失败，请联系管理员");
}

    /**
     * 修改班级信息
     * @param gclass
     * @return
     */
    @PutMapping("/updateClass")
    public Result updateClass(@RequestBody Gclass gclass){
        System.out.println(gclass);
        QueryWrapper<Gclass> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("t_id",gclass.getTid());
        Gclass gclass1=gclassService.getOne(queryWrapper);
        //boolean flag1=gclassService.updateById(gclass);
        if(gclass1==null){
            gclassService.updateGclass(gclass);
            return Result.success("修改成功");
        }
        return Result.error("该老师已管理其他班级");

    }

    /**
     * 新增班级信息
     * @param gclass
     * @return
     */
    @PostMapping("/insertClass")
    public Result insertClass(@RequestBody Gclass gclass){
        //首先判断该班级是否存在
        QueryWrapper<Gclass> gclassQueryWrapper=new QueryWrapper<>();
        gclassQueryWrapper.eq("name",gclass.getName());
        Gclass gclass1=gclassService.getOne(gclassQueryWrapper);
        //存在返回数据，添加失败
        if(!"".equals(gclass1) && null!=gclass1){
            return Result.error("该班级已存在，新增失败");
        }else {
            gclass.setDeleted(0);
            gclassService.save(gclass);
            return Result.success("新增成功");
        }

    }

    /**
     *根据id删除班级信息
     * @return
     */
    @DeleteMapping("/DelClassById/{id}")
    public Result DelClassById(@PathVariable("id") Long id){
        QueryWrapper<Gclass> gclassQueryWrapper=new QueryWrapper<>();
        gclassQueryWrapper.eq("id",id);
        Gclass gclass1=gclassService.getOne(gclassQueryWrapper);
        if(!"".equals(gclass1) && null!=gclass1) {
            gclassService.removeById(id);
            UpdateWrapper<Student> queryWrapper = new UpdateWrapper<>();
            queryWrapper.in("class_id",id).set("class_id",null);
            studentService.update(queryWrapper);
            return Result.success("删除成功");
        }
        return Result.error("该班级不存在，删除失败");
    }

    /**
     * 批量删除班级信息
     * @param idList
     * @return
     */
    @DeleteMapping("/delClassBatch")
    public Result delClassBatch(@RequestBody List<Long> idList){
        if(idList.size()>0){
            UpdateWrapper<Student> queryWrapper = new UpdateWrapper<>();
            queryWrapper.in("class_id",idList).set("class_id",null);
            studentService.update(queryWrapper);
            gclassService.removeBatchByIds(idList);
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    /**
     * 给班级添加管理老师
     * @return
     */
    @PutMapping("/addClassTeacher")
    public Result addClassTeacher(@RequestBody Gclass gclass){
        //首先查询该老师是否已经管理班级
        QueryWrapper<Gclass> gclassQueryWrapper=new QueryWrapper<>();
        gclassQueryWrapper.eq("t_id",gclass.getTid());
        Gclass gclass1=gclassService.getOne(gclassQueryWrapper);
        if(gclass1==null){
            UpdateWrapper<Gclass> queryWrapper=new UpdateWrapper<>();
            queryWrapper.eq("id",gclass.getId()).set("t_id",gclass.getTid());
            gclassService.update(queryWrapper);
            return Result.success("添加成功");
        }
        return Result.error("不可以重复管理班级");
    }















}
