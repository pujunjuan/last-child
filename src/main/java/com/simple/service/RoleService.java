package com.simple.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.simple.domian.Role;
import com.simple.domian.Student;

public interface RoleService extends IService<Role> {
    IPage<Role> getRoleByOpr(Page<Role> page, Role role);
}
