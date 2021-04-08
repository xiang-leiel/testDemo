package com.example.test.service;

import com.example.test.dao.UserInfoMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description 
 * @author leiel
 * @Date 2020/8/5 2:20 PM
 */
@Component
public class UserInfoService1 {

    @Resource
    UserInfoMapper userInfoMapper;

    public void query() {
        System.out.println(userInfoMapper.selectOne(1));
    }

}
