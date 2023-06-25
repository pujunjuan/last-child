package com.simple.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.simple.domian.Student;
import com.simple.domian.Teacher;
import com.simple.domian.User;

public interface UserService extends IService<User> {
    User getNewUser();
    IPage<User> getUserOpr(IPage<User> page,User user);
    }
