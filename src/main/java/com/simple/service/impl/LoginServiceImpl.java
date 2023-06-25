package com.simple.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.common.RedisCache;
import com.simple.common.result.Result;
import com.simple.domian.LoginUser;
import com.simple.mapper.LoginMapper;
import com.simple.domian.User;
import com.simple.service.LoginService;
import com.simple.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Objects;


@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper,User> implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    RedisCache redisCache;

    @Override
    public Result login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getAccount(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            //throw new RuntimeException("用户名或密码错误");
            return  Result.error(400,"登陆失败");
        }
        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();

        String jwt = JwtUtil.createJWT(userId);
        System.out.println(userId+loginUser);
        System.out.println("jwt"+jwt);
        //authenticate存入redis\
        System.out.println(3333);
        redisCache.putObject("login:"+userId,loginUser);
        System.out.println(1111);
        System.out.println(redisCache.getObject("login"));
        //把token响应给前端
        HashMap<String,String> map = new HashMap<>();
        map.put("token",jwt);
        System.out.println(222);
        return new Result(200,"登陆成功",map);
    }


    @Override
    public Result logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       LoginUser loginUser = (LoginUser) authentication.getPrincipal();
      // LoginUser loginUser = JSON.parseObject(JSON.toJSON(authentication.getPrincipal()).toString(), LoginUser.class);
        Long userid = loginUser.getUser().getId();
        redisCache.removeObject("login:"+userid);
        return new Result(200,"退出成功");
    }

    @Override
    public User LoginInfo(Long account, String password) {
        return this.baseMapper.Login(account,password);
    }

}

