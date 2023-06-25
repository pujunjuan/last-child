package com.simple.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.result.CommonPage;
import com.simple.common.result.Result;
import com.simple.domian.Role;
import com.simple.domian.Student;
import com.simple.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 返回所有角色信息
     * @return
     */
    @GetMapping("/getRole")
    public Result getRole(){
        return Result.success(roleService.list());
    }

    /**
     * 分页查询角色
     * @param pageNo
     * @param pageSize
     * @param role
     * @return
     */
    @GetMapping("/getRoleByOpr")
    public Result getRoleByOpr(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                               Role role){
        //分页信息封装Page对象
        Page<Role> page = new Page<>(pageNo,pageSize);
        //进行查询
        IPage<Role> studentIPage = roleService.getRoleByOpr(page,role);
        CommonPage commonPage=new CommonPage();
        commonPage.setPageNum(pageNo);
        commonPage.setPageSize(pageSize);
        commonPage.setTotal(studentIPage.getTotal());
        commonPage.setList(studentIPage.getRecords());
        //封装Result返回
        return Result.success(commonPage);
    }

    /**
     * 修改角色
     */
    @PutMapping("/updateRole")
    public Result updateStudent(@RequestBody Role role){
        UpdateWrapper<Role> updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("name",role.getName()).eq("id",role.getId()).or().set("role_key",role.getRoleKey());
        roleService.update(updateWrapper);
        return Result.success("修改成功");
    }

    /**
     * 添加角色
     */
    @PostMapping("/insertRole")
    public Result insertRole(@RequestBody Role role){
        System.out.println(role);
       roleService.save(role);
       return Result.success("添加成功");
    }

}
