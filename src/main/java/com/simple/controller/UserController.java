package com.simple.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.result.CommonPage;
import com.simple.common.result.Result;
import com.simple.domian.Student;
import com.simple.domian.StudentParent;
import com.simple.domian.Teacher;
import com.simple.domian.User;
import com.simple.service.OssService;
import com.simple.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OssService fileUploadService;


    /**
     * 返回所有用户信息
     */
    @GetMapping("/getUserList")
    public Result getUserList(){
        return Result.success(userService.list());
    }


    /**
     * 分页返回所有管理员信息
     * @param pageNo
     * @param pageSize
     * @param user
     * @return
     */
    @GetMapping("/getUserOpr")
    public Result getUserOpr(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                    @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                    User user){
        Page<User> page = new Page<>(pageNo,pageSize);
        user.setType(4);
        IPage<User> courseIPage=userService.getUserOpr(page,user);
        CommonPage commonPage=new CommonPage();
        commonPage.setPageNum(pageNo);
        commonPage.setPageSize(pageSize);
        commonPage.setTotal(courseIPage.getTotal());
        commonPage.setList(courseIPage.getRecords());
        //封装Result返回
        return Result.success(commonPage);
    }

    /**
     * 根据用户id查询信息
     * @param Tno
     * @return
     */
    @GetMapping("/getUserById")
    public Result getUserById(@RequestParam("id") Long Tno){
        User user=userService.getById(Tno);
        return Result.success(user);

    }
    /**
     * 根据用户id查询信息
     * @param account
     * @return
     */
    @GetMapping("/getUserByAccount")
    public Result getUserByAccount(@RequestParam("account") Long account){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("account",account);
        User user=userService.getOne(queryWrapper);
        return Result.success(user);

    }
    /**
     * 新增用户
     */
    @PostMapping("/addUser")
    public Result updateUserRole(@RequestBody User user){
        user.setAvatar("https://garten.oss-cn-hangzhou.aliyuncs.com/men.png");
        user.setCreateTime(new Date());
        user.setPassword("1234");
        user.setType(4);
        boolean flag=userService.save(user);
        if(flag){
            return Result.success();
        }
        return Result.error();
    }

    /**
     * 修改用户角色
     */
    @PutMapping("/updateUserRole")
    public Result updateUserRole(@RequestParam("id") Long id,@RequestParam("type") Long type){
        UpdateWrapper<User> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",id).set("type",type);
        boolean flag=userService.update(updateWrapper);
        if(flag){
            return Result.success();
        }
        return Result.error();
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/deleteUserById")
    public Result deleteUserById(@RequestBody Long idList){
            userService.removeById(idList);
            return Result.success("删除成功");
    }

    /**
     * 批量删除用户
     */
    @DeleteMapping("/deleteUserByIds")
    public Result deleteUserByIds(@RequestBody List<Long> idList){
        System.out.println(idList);
        if(idList.size()>0){
            userService.removeBatchByIds(idList);
            return Result.success("删除成功");
        }
        return Result.success("删除失败");

    }

    /**
     * 修改用户角色密码
     * @param id
     * @param oldPassword 旧密码
     * @param password 新密码
     * @return
     */
    @PostMapping("/updateUserPwd")
    public Result updateUserRole(@RequestParam("id") Long id,@RequestParam("oldPassword") String oldPassword,@RequestParam("password") String password){
        //给新密码赋值
        UpdateWrapper<User> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",id).set("password",password);
        //查询旧密码是否存在
        UpdateWrapper<User> updateWrapper1=new UpdateWrapper<>();
        updateWrapper1.eq("id",id).eq("password",oldPassword);
        User flag=userService.getOne(updateWrapper1);
        if(flag!=null){
            String pwd=flag.getPassword();
            System.out.println(pwd==password);
            if(!pwd.equals(password)){
                userService.update(updateWrapper);
                return Result.success();
            }
            return Result.error("新密码不能与旧密码相同");
        }
        return Result.error("修改密码失败，旧密码错误");
    }


    /**
     * 修改头像，根据用户账号修改
     * @return result
     */
    @PostMapping("/avatar")
    public Result avatar(@RequestParam("file") MultipartFile file,@RequestParam("id") Long id) throws Exception
    {
        System.out.println("============"+file);
        if (file.isEmpty()){
            return Result.error (400,"上传图片为空！");
        }
        String photo=fileUploadService.upload(file).getName();
        UpdateWrapper<User> userQueryWrapper=new UpdateWrapper<>();
        userQueryWrapper.eq("account",id).set("avatar",photo);
        boolean flag=userService.update(userQueryWrapper);
        if(!flag){
            return Result.error (400,"修改头像失败，请稍后重试！");
        }
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("account",id);
        User user=userService.getOne(queryWrapper);
        return Result.success("修改头像成功",user);

    }



}
