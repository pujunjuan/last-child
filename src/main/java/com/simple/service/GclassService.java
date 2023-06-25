package com.simple.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.simple.domian.Gclass;

public interface GclassService extends IService<Gclass> {

    IPage<Gclass> getGclassByOpr(Page page,Gclass gclass);

    int updateGclass(Gclass gclass);
}

