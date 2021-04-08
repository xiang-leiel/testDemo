package com.example.test.service.impl;

import com.example.test.entity.UserInfo;
import com.example.test.dao.UserInfoMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.test.service.UserInfoSerivice;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author leiel
 * @since 2020-03-04
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoSerivice {

}
