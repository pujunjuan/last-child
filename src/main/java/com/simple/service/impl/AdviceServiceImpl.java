package com.simple.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.domian.Advice;
import com.simple.mapper.AdviceMapper;
import com.simple.service.AdviceService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AdviceServiceImpl extends ServiceImpl<AdviceMapper,Advice> implements AdviceService {
    @Override
    public IPage<Advice> getAdviceOpr(IPage<Advice> page, Advice advice) {
        QueryWrapper<Advice> queryWrapper = new QueryWrapper<>();
        queryWrapper.select().orderByDesc("status").orderByDesc("create_time");
        if (advice == null) {
            return this.baseMapper.getAdviceOpr(page, queryWrapper);
        }
        Long id = advice.getId();
        Long tid = advice.getTid();
        Map<String, Object> time=advice.getParams();
        int status=advice.getStatus();
        if(time!=null){
            queryWrapper.apply(StringUtils.checkValNotNull(advice.getParams().get("beginTime")),
                    "date_format (a.create_time,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", advice.getParams().get("beginTime")).
                    apply(StringUtils.checkValNotNull(advice.getParams().get("endTime")),
                            "date_format (a.create_time,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", advice.getParams().get("endTime"));

        }
        if (!"".equals(id) && null != id && id != 0) {
            queryWrapper.eq("a.id", id);
        }
        if (!"".equals(tid) && null != tid && tid != 0) {
            queryWrapper.eq("a.t_id", tid);
        }
        if (!"".equals(status)  && status != 0) {
            queryWrapper.eq("a.status", status);
        }
        return this.baseMapper.getAdviceOpr(page, queryWrapper);
    }
}
