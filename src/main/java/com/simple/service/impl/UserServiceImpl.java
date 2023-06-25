package com.simple.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.common.RedisCache;
import com.simple.config.RedisConfig;
import com.simple.domian.CookBook;
import com.simple.mapper.UserMapper;
import com.simple.domian.Student;
import com.simple.domian.Teacher;
import com.simple.domian.User;
import com.simple.service.UserService;
import com.simple.util.PinYinUtil;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    //获取最新插入的一条数据
    public User getNewUser(){
        return this.baseMapper.getNewUser();
    }

    @Override
    public IPage<User> getUserOpr(IPage<User> page, User user) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().orderByDesc(User::getType);
        if(user==null){
            return this.baseMapper.getUserOpr(page,queryWrapper);
        }
        Long account=user.getAccount();
        Integer type=user.getType();
        if(!"".equals(account) && null!=account && account!=0){
            queryWrapper.eq("account",account);
        }
        if(!"".equals(type) && null!=type){
            queryWrapper.eq("type",type);
        }
        queryWrapper.eq("deleted",0);
        return this.baseMapper.getUserOpr(page,queryWrapper);
    }


}
