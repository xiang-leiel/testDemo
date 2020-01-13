package com.example.test.impl.user;

import com.example.test.entity.user.UserInfo;
import com.example.test.mapper.user.UserInfoMapper;
import com.example.test.service.user.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author leiel
 * @since 2020-01-13
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

}
