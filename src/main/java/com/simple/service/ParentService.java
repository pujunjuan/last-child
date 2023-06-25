package com.simple.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.simple.domian.Parent;

public interface ParentService extends IService<Parent> {
    /**
     * 分页条件查询
     * @param page
     * @param parent
     * @return
     */
    IPage<Parent> getParentByOpr(Page<Parent> page, Parent parent);

    /**
     * 修改家长信息
     * @param parent
     * @return
     */
    int updateParent(Parent parent);
}
