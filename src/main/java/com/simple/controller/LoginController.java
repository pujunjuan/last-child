package com.simple.controller;
import com.simple.common.result.Result;
import com.simple.common.result.ResultCode;
import com.simple.domian.User;
import com.simple.service.LoginService;
import com.simple.service.UserService;
import com.simple.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.security.util.PendingException;

import javax.servlet.http.HttpServletRequest;
import javax.sound.midi.Soundbank;
import java.security.acl.LastOwnerException;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    LoginService loginService;

    JwtUtil jwtUtil;

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        Long account=user.getAccount();
        String password=user.getPassword();
        User user1=loginService.LoginInfo(account,password);
        if(user1!=null){
            return Result.success(user1);
        }
        return Result.error();
    }

//    @PostMapping("/login")
//    public Result login(@RequestBody User user){
//        System.out.println(user);
//        return  loginService.login(user) ;
//
//    }

    @RequestMapping("/logout")
    public Result logout(){
        return loginService.logout();
    }

    //登录成功后获取用户信息
    @PostMapping("/profile")
    public Result profile(HttpServletRequest request) throws PendingException, Exception {

        //1.获取请求头信息：名称=Authorization(前后端约定)
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization)) {
            throw new RuntimeException("无 token ，请重新登陆");
        }
        //2.替换Bearer+空格
        String token = authorization.replace("Bearer ", "");

        //3.解析token
        Claims claims = jwtUtil.parseJWT(token);
        //4.获取clamis
        System.out.println(claims);
        String userId = (String) claims.get("userid");
        System.out.println("id : " +claims.get("userid"));
        // String userId = "U01";
        User user = userService.getById(userId);
/**此处只是为了获取token中的用户数据，所有只简单返回用户对象，
 * 工作则按实际要求多表查询需要数据（根据用户ID查询权限）
 */
        return  Result.success(user);
    }




}
