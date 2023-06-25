package com.simple.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.domian.Role;
import com.simple.domian.User;
import com.simple.mapper.RoleMapper;
import com.simple.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public IPage<Role> getRoleByOpr(Page<Role> page, Role role) {
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().orderByDesc(Role::getId);
        if(role==null){
            return this.baseMapper.selectByPage(page,queryWrapper);
        }
        String name=role.getName();
        Long id=role.getId();
        String roleKey=role.getRoleKey();
        if(!"".equals(id) && null!=id && id!=0){
            queryWrapper.eq("id",id);
        }
        if(!"".equals(name) && null!=name){
            queryWrapper.eq("name",name);
        }
        if(!"".equals(roleKey) && null!=roleKey){
            queryWrapper.eq("role_key",roleKey);
        }
        return this.baseMapper.selectByPage(page,queryWrapper);
    }
}
