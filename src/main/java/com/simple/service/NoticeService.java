package com.simple.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.simple.domian.Notice;

public interface NoticeService extends IService<Notice> {
    IPage<Notice> getNoticeOpr(IPage<Notice> page,Notice notice);
    boolean updateNotice(Notice notice);
    Notice getNewOneNotice();
}
