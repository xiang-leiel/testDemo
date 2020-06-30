package com.quantchi.tianji.service.search.service.impl.user;

import com.quantchi.tianji.service.search.entity.user.UserInfo;
import com.quantchi.tianji.service.search.dao.user.UserInfoMapper;
import com.quantchi.tianji.service.search.service.user.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author leiel
 * @since 2020-06-29
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

}
