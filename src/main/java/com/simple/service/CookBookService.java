package com.simple.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.simple.domian.CookBook;

public interface CookBookService extends IService<CookBook> {
    IPage<CookBook> getCookBookOpr(IPage<CookBook> page,CookBook cookBook);
    boolean updateCookBook(CookBook cookBook);
}
