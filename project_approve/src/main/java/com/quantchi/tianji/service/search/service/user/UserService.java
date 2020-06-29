package com.quantchi.tianji.service.search.service.user;

import com.quantchi.core.message.ResultInfo;

/**
 * @author leiel
 * @Description
 * @Date 2020/6/29 10:50 AM
 */
public interface UserService {

    ResultInfo searchAuthority(String staffId);

    ResultInfo getProjectMember(String visitId);

}
