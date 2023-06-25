package com.simple.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simple.common.result.Result;
import com.simple.domian.User;

public interface LoginService extends IService<User>{
     Result login(User  user);
     Result logout();

     User LoginInfo(Long account,String password);

}
