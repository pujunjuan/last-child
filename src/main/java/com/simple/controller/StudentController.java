package com.simple.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.result.CommonPage;
import com.simple.common.result.Result;
import com.simple.domian.Student;
import com.simple.domian.StudentParent;
import com.simple.domian.User;
import com.simple.service.StudentParentService;
import com.simple.service.StudentService;
import com.simple.service.UserService;
import com.simple.util.PinYinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentParentService studentParentService;

    /**
     * 条件查询学生班级id
     * @param id)
     * @return
     */
    @GetMapping("/getStudentWhere")
    public Result getStudentWhere(@RequestParam Long id){
        QueryWrapper<Student> studentQueryWrapper=new QueryWrapper<>();
        studentQueryWrapper.eq("class_id",id);
        List<Student> list= studentService.list(studentQueryWrapper);
        return  Result.success(list);
    }

    /**
     * 根据学生学号查询信息
     * @param id
     * @return
     */
    @GetMapping("/getStudentById")
    public Result getStudengById(@RequestParam Long id){
        Student student=studentService.getById(id);
        return Result.success(student);

    }

    /**
     * 获取学生信息
     * @param
     * @return
     */
    @GetMapping("/getStudentInfo")
    public Result getStudentInfo(){
        return Result.success(studentService.list());

    }

    /**
     * 分页查询学生
     * @param pageNo
     * @param pageSize
     * @param student
     * @return
     */
    @GetMapping("/getLeaveByOpr")
    public Result getLeaveByOpr(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                  Student student){
        System.out.println(student);
        //分页信息封装Page对象
        Page<Student> page = new Page<>(pageNo,pageSize);
        //进行查询
        IPage<Student> studentIPage = studentService.getLeaveByOpr(page,student);
        CommonPage commonPage=new CommonPage();
        commonPage.setPageNum(pageNo);
        commonPage.setPageSize(pageSize);
        commonPage.setTotal(studentIPage.getTotal());
        commonPage.setList(studentIPage.getRecords());
        //封装Result返回
        return Result.success(commonPage);
    }

    /**
     * 分页查询学生
     * @param pageNo
     * @param pageSize
     * @param student
     * @return
     */
    @GetMapping("/getStudentByOpr")
    public Result getStudentByOpr(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                  Student student){
        //分页信息封装Page对象
        Page<Student> page = new Page<>(pageNo,pageSize);
        //进行查询
        IPage<Student> studentIPage = studentService.getStudentByOpr(page,student);
        CommonPage commonPage=new CommonPage();
        commonPage.setPageNum(pageNo);
        commonPage.setPageSize(pageSize);
        commonPage.setTotal(studentIPage.getTotal());
        commonPage.setList(studentIPage.getRecords());
        //封装Result返回
        return Result.success(commonPage);
    }

    /**
     * 条件修改学生信息
     * @param student
     * @return
     */
    @PutMapping("/updateStudent")
    public Result updateStudent(@RequestBody Student student){
        Student student1=studentService.getById(student.getId());
        if(!"".equals(student1) && null != student1){
            int flag=studentService.updateStudent(student);
            if(flag!=0){
                return Result.success();
            }
        }
        return Result.error(400,"学生信息不存在");
    }


    /**
     * 学生修改密码
     */
    @PutMapping("/updateStuPwd")
    public Result updateStuPwd(@RequestParam("id") Long id,@RequestParam String newPwd){
        //查询用户是否存在
        Student student=studentService.getById(id);
        if(!"".equals(student) && null != student){
            UpdateWrapper<User> updateWrapper=new UpdateWrapper<>();
            updateWrapper.eq("account",id).set("password",newPwd);
            boolean flag=userService.update(updateWrapper);
            if(flag){
                return Result.success();
            }
        }
        return Result.error("修改密码异常，请联系管理员");
    }


    /**
     * 添加学生信息
     * @param student
     * @return
     */
    @PostMapping("/insertStudent")
    public Result insertStudent(@RequestBody Student student){
        //判断是否写入学生学号
        Long id=student.getId();
        if(id!=null){
            //判断他学号是否存在
//            QueryWrapper<User> queryWrapper1=new QueryWrapper<>();
//            queryWrapper1.eq("account",student.getId());
            Student student1=studentService.getById(id);
          //  User user1=userService.getOne(queryWrapper1);
            if(student1==null){
                User user=new User();
                user.setAccount(student.getId());
                user.setAvatar("https://garten.oss-cn-hangzhou.aliyuncs.com/men.png");
                user.setPassword("1234");
                user.setType(2);
                user.setCreateTime(new Date());
                userService.save(user);
                QueryWrapper<User> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("account",student.getId());
//                User user1=userService.getOne(queryWrapper);
                student.setUsId(user.getId());
                student.setTime(new Date());
                studentService.save(student);
                return Result.success("新增成功");
            }
                return Result.error("该学号已存在");

        }
       return Result.error("学号不能为空");

    }



    /**
     * 管理员添加学生账号
     * @param student
     * @return
     */
    @PostMapping("/insertStudent1")
    public Result insertStudent1(@RequestBody Student student){
        //判断是否写入学生学号
        Long id=student.getId();
        if(id!=null){
            //判断他学号是否存在
//            QueryWrapper<User> queryWrapper1=new QueryWrapper<>();
//            queryWrapper1.eq("account",student.getId());
            Student student1=studentService.getById(id);
            //  User user1=userService.getOne(queryWrapper1);
            if(student1==null){
                User user=new User();
                user.setAccount(student.getId());
                user.setAvatar("https://garten.oss-cn-hangzhou.aliyuncs.com/men.png");
                user.setPassword("1234");
                user.setType(2);
                user.setCreateTime(new Date());
                userService.save(user);
                QueryWrapper<User> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("account",student.getId());
//                User user1=userService.getOne(queryWrapper);
                student.setUsId(user.getId());
                student.setTime(new Date());
                studentService.save(student);
                return Result.success("新增成功");
            }
            return Result.error("该学号已存在");

        }
        return Result.error("学号不能为空");

    }

    /**
     * 删除学生信息
     * @param id
     * @return
     */
    @DeleteMapping("/delStudentById")
    public Result delStudentById(@RequestBody Long id){
        Student student=studentService.getById(id);
        Long uid=student.getUsId();
        if(!"".equals(student) && null != student){
            studentService.removeById(id);
            QueryWrapper<StudentParent> queryWrapper=new QueryWrapper<>();
            queryWrapper.in("student_id",id);
            studentParentService.remove(queryWrapper);
            userService.removeById(uid);
            return Result.success("删除成功");
        }
        return Result.error("删除失败");

    }


    /**
     * 批量删除学生信息
     * @param idList
     * @return
     */
    @DeleteMapping("/delStuBatch")
    public Result delStuBatch( @RequestBody List<Long> idList){

        if(idList.size()>0){
            //获取学生信息，根据学生id获取uId
            List<Student> list = studentService.listByIds(idList);
            Collection<Long> arrayList=new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                arrayList.add((list.get(i).getUsId()));
            }
            //先删除学生表的学生基本信息
            studentService.removeBatchByIds(idList);

            //删除和家长关联的数据表
            QueryWrapper<StudentParent> queryWrapper1=new QueryWrapper<>();
            queryWrapper1.in("student_id",idList);
            studentParentService.remove(queryWrapper1);

            QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
            //在删除用户表中学生的账号密码
            userService.removeBatchByIds(arrayList);
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }


}
