package com.quantchi.tianji.service.search.dao.user;

import com.quantchi.tianji.service.search.entity.user.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author leiel
 * @since 2020-06-29
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    UserInfo selectBymobile(String mobile);
}
