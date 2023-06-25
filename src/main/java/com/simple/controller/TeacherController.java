package com.simple.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.result.CommonPage;
import com.simple.common.result.Result;
import com.simple.domian.Student;
import com.simple.domian.Teacher;
import com.simple.domian.User;
import com.simple.service.TeacherService;
import com.simple.service.UserService;
import com.simple.util.PinYinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @Autowired
    UserService userService;

    /**
     * 分页查询老师信息或许条件分页查询
     * @param pageNo
     * @param pageSize
     * @param teacher
     * @return
     */
    @GetMapping("/getTeacherByOpr")
    public Result getTeacherByOpr(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                  Teacher teacher){
        //分页信息封装Page对象
        Page<Teacher> page = new Page<>(pageNo,pageSize);
        //进行查询
        IPage<Teacher> teacherIPage = teacherService.getTeacherByOpr(page,teacher);
        //封装Result返回
        CommonPage commonPage=new CommonPage();
        commonPage.setPageNum(pageNo);
        commonPage.setPageSize(pageSize);
        commonPage.setTotal(teacherIPage.getTotal());
        commonPage.setList(teacherIPage.getRecords());
        //封装Result返回
        return Result.success(commonPage);
    }


    /**
     * 获取老师信息
     * @param
     * @return
     */
    @GetMapping("/getTeacherInfo")
    public Result getTeacherInfo(){
        return Result.success(teacherService.list());

    }

    /**
     * 根据老师教工号查询信息
     * @param Tno
     * @return
     */
    @GetMapping("/getTeacherById")
    public Result getTeacherNo(@RequestParam("id") Long Tno){
        Teacher teacher=teacherService.getById(Tno);
        return Result.success(teacher);

    }

    /**
     * 老师姓名、教工号、电话号码、性别条件查询
     * @param teacher
     * @return
     */
    @GetMapping("/getTeacherWhere")
    public Result getTeacherWhere(@RequestBody Teacher teacher){
        QueryWrapper<Teacher> queryWrapper=new QueryWrapper<>();
        // 1、构造条件查询
        String name = teacher.getName();
        Long sno = teacher.getId();
        String  phone= teacher.getTelephone();
        String sex = teacher.getSex();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.likeRight("name", name);
        }
        if (sno != null) {
            queryWrapper.eq("id", sno);
        }
        if (!StringUtils.isEmpty(phone)) {
            queryWrapper.eq("telephone", phone);
        }
        if (!StringUtils.isEmpty(sex)) {
            queryWrapper.eq("sex", sex);
        }
        List<Object> list = teacherService.listObjs(queryWrapper);
        return Result.success(list);
    }


    /**
     * 更新老师信息
     * @param teacher
     * @return
     */
    @PutMapping("/updateTeacher")
    public Result updateTeacher(@RequestBody Teacher teacher){
        Teacher TeaInfo=teacherService.getById(teacher.getId());
        if(!"".equals(TeaInfo) && null != TeaInfo){
            int flag=teacherService.updateTeacher(teacher);
            if(flag!=0){
                return Result.success();
            }
        }
        return Result.error(400,"老师信息不存在");
    }

    /**
     * 老师修改密码
     * @param id
     * @param newPwd
     * @return
     */
    @PutMapping("/updateTeaPwd")
    public Result updateTeacherPwd(@RequestParam("id") Long id,@RequestParam String newPwd){
        //查询用户是否存在
        Teacher teacher=teacherService.getById(id);
        if(!"".equals(teacher) && null != teacher){
            UpdateWrapper<User> updateWrapper=new UpdateWrapper<>();
            System.out.println(111);
            updateWrapper.eq("account",id).set("password",newPwd);
            System.out.println(222);
            boolean flag=userService.update(updateWrapper);
            System.out.println(flag);
            if(flag){
                return Result.success();
            }
        }
        return Result.error("修改密码异常，请联系管理员");

    }

    /**
     * 新增老师信息
     * @param teacher
     * @return
     */
    @PostMapping("insertTeacher")
    public Result insertTeacher(@RequestBody Teacher teacher){
        //插入数据到老师表，教师工号自动生成
        teacher.setJobTime(new Date());
        teacher.setUsId(1);
        teacherService.save(teacher);
        Teacher teacher1=teacherService.getNewTeacher();
        //插入成功后，将教工号插入用户表
        User user=new User();
        user.setAvatar("https://garten.oss-cn-hangzhou.aliyuncs.com/teacher.png");
        user.setAccount(teacher1.getId());
        user.setPassword("1234");
        user.setType(1);
        user.setCreateTime(new Date());
        userService.save(user);
        // //插入用户表成功后，将用户id插入教师表
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("account",teacher.getId());
        User user1=userService.getOne(queryWrapper);
        UpdateWrapper<Teacher> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",teacher1.getId()).set("u_id",user1.getId());
        teacherService.update(updateWrapper);
        return Result.success("新增成功");
    }

    /**
     * 根据老师教工号删除信息
     * @param id
     * @return
     */
    @DeleteMapping("/delTeacherById")
    public Result delTeacher(@RequestBody Long id)
    {
        //1、判断用户是否存在
        Teacher teacher=teacherService.getById(id);
        if(!"".equals(teacher) && null !=teacher){
            Long uId=teacher.getUsId();
            teacherService.removeById(id);
            userService.removeById(uId);
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    /**
     * 根据老师教工号批量删除信息
     * @param idList
     * @return
     */
    @DeleteMapping("/delTeacherByIds")
    public Result delStuBatch(@RequestBody List<Long> idList){
        //获取教师信息，根据教师id获取uId
        if(idList.size()>0){
            List<Teacher> list = teacherService.listByIds(idList);
            Collection<Long> arrayList=new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                arrayList.add((list.get(i).getUsId()));
            }
            //先删除教师表的教师基本信息
            teacherService.removeBatchByIds(idList);
            //在删除用户表中教师的账号密码
            userService.removeBatchByIds(arrayList);
            return Result.success("删除成功");
        }
        return Result.error("删除失败");

    }




}
