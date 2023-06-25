package com.simple.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.domian.Notice;
import com.simple.mapper.NoticeMapper;
import com.simple.service.NoticeService;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Map;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    public IPage<Notice> getNoticeOpr(IPage<Notice> page, Notice notice) {
        QueryWrapper<Notice> queryWrapper=new QueryWrapper<>();
        queryWrapper.select().orderByDesc("create_time");
        if(notice==null){
            return this.baseMapper.getNoticeOpr(page,queryWrapper);
        }

        Map<String, Object> time=notice.getParams();
        Long id=notice.getId();
        String title=notice.getTitle();
        Date time1=notice.getCreateTime();
        if(time!=null){
            queryWrapper.apply(StringUtils.checkValNotNull(notice.getParams().get("beginTime")),
                    "date_format (create_time,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", notice.getParams().get("beginTime")).
                    apply(StringUtils.checkValNotNull(notice.getParams().get("endTime")),
                            "date_format (create_time,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", notice.getParams().get("endTime"));

        }
        if(!"".equals(id)&& null!=id&& id!=0){
            queryWrapper.eq("id",id);
        }
        if(!StringUtils.isEmpty(title)){
            queryWrapper.like("title",title);
        }
        if(time1!=null){
            queryWrapper.apply(StringUtils.checkValNotNull(time1),
                    "date_format (create_time,'%Y-%m-%d') = date_format ({0},'%Y-%m-%d')",time1);
        }
        return this.baseMapper.getNoticeOpr(page,queryWrapper);
    }

    @Override
    public boolean updateNotice(Notice notice) {
        return this.baseMapper.updateNotice(notice);
    }

    @Override
    public Notice getNewOneNotice() {
        return this.baseMapper.getNewOneNotice();
    }
}
