package com.simple.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.domian.CookBook;
import com.simple.mapper.CookBookMapper;
import com.simple.service.CookBookService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class CookBookServiceImpl extends ServiceImpl<CookBookMapper, CookBook> implements CookBookService {

    public IPage<CookBook> getCookBookOpr(IPage<CookBook> page, CookBook cookBook) {
       QueryWrapper<CookBook> queryWrapper =new QueryWrapper<>();
       queryWrapper.lambda().orderByDesc(CookBook::getCreateTime);
        // 按create_time排序，若create_time相同，则按id排序
        if(cookBook==null){
            return this.baseMapper.getCookBookOpr(page,queryWrapper);
        }
        String title=cookBook.getTitle();
        String type=cookBook.getType();
        Date time1=cookBook.getCreateTime();
        System.out.println(time1);
        Map<String, Object> time=cookBook.getParams();
        if(time!=null){
            queryWrapper.apply(StringUtils.checkValNotNull(cookBook.getParams().get("beginTime")),
                    "date_format (create_time,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", cookBook.getParams().get("beginTime")).
                    apply(StringUtils.checkValNotNull(cookBook.getParams().get("endTime")),
                            "date_format (create_time,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", cookBook.getParams().get("endTime"));

        }
        if(!StringUtils.isEmpty(title)){
            queryWrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(type)){
            queryWrapper.eq("type",type);
        }
        if(time1!=null){
            queryWrapper.apply(StringUtils.checkValNotNull(time1),
                    "date_format (create_time,'%Y-%m-%d') = date_format ({0},'%Y-%m-%d')",time1);
        }
        return this.baseMapper.getCookBookOpr(page,queryWrapper);
    }

    @Override
    public boolean updateCookBook(CookBook cookBook) {
        return this.baseMapper.updateCookBook(cookBook);
    }
}
