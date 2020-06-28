package com.quantchi.tianji.service.search.service.sign.impl;

import com.quantchi.tianji.service.search.dao.InvUserDetailMapper;
import com.quantchi.tianji.service.search.model.UserInfoNew;
import com.quantchi.tianji.service.search.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/11 5:12 PM
 */
@Slf4j
@Service
public class UserInfoService {

    @Resource
    private InvUserDetailMapper invUserDetailMapper;

    public UserInfo selectUserInfo(String staffId) {
        //获取staffId对应的个人信息
        UserInfo userInfo = invUserDetailMapper.getUserInfoByStaffId(staffId);
        return userInfo;
    }

    /**
     * 获取审批人相关信息
     * @param mobile
     * @return
     */
    public UserInfoNew queryLoginUserInfo(String mobile) {
        UserInfoNew loginUserInfo = new UserInfoNew();

        return loginUserInfo;
    }

}
