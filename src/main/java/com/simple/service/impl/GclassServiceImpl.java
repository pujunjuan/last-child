package com.simple.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.domian.Gclass;
import com.simple.mapper.GclassMapper;
import com.simple.service.GclassService;
import org.springframework.stereotype.Service;

@Service
public class GclassServiceImpl extends ServiceImpl<GclassMapper, Gclass> implements GclassService {

    @Override
    public IPage<Gclass> getGclassByOpr(Page page, Gclass gclass) {
        QueryWrapper<Gclass> queryWrapper=new QueryWrapper<>();
        //无条件分页查询语句
        if(gclass==null){
            return this.baseMapper.getGclassByOpr(page,queryWrapper);
        }
        //有条件分页查询语句
        String name = gclass.getName();
        Long teacher = gclass.getTid();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.likeRight("a.name",name);
        }
        if (!"".equals(teacher) && teacher != null && teacher!=0) {
            queryWrapper.eq("t_id", teacher);
        }
       queryWrapper.eq("a.deleted",0);

        return this.baseMapper.getGclassByOpr(page,queryWrapper);
    }


    public int updateGclass(Gclass gclass) {
        return this.baseMapper.updateGclass(gclass);
    }
}
