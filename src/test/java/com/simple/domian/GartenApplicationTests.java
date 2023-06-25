package com.simple.domian;

import com.simple.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class GartenApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Test
    public void findTest(){

        System.out.println("*****************************************************************************************");
        User list=  userMapper.selectById(1);
        System.out.println(list);

    }




}
