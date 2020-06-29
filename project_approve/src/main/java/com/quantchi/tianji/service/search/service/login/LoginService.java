package com.quantchi.tianji.service.search.service.login;

import com.quantchi.tianji.service.search.dao.mapper.user.UserInfoMapper;
import com.quantchi.tianji.service.search.entity.user.UserInfo;
import com.quantchi.tianji.service.search.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/29 10:21 AM
 */
@Slf4j
@Service
public class LoginService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource

    public Map<String,Object> checkMobile(String mobile, String passWord) {

        Map<String, Object> loginMap = new HashMap<>(10);

        //查询用户基本信息表获取用户是否录入
        UserInfo userInfo = userInfoMapper.selectBymobile(mobile);

        String md5Passwd = MD5Utils.encode(passWord);

        if(userInfo == null || (!userInfo.getLoginPwd().equals(passWord) && !userInfo.getLoginPwd().equals(md5Passwd))) {
            log.info("用户基本信息未初始化", mobile);
            return null;
        }

        //获取人员岗位关系代码
/*        List<DmWfRygwdzb> dmWfRygwdzbList = dmWfRygwdzbMapper.selectByUserDm(userInfo.getId());
        if(CollectionUtils.isEmpty(dmWfRygwdzbList)) {
            log.info("用户人员岗位关系未设置{}", mobile);
            return null;
        }
        if(dmWfRygwdzbList.size() == 1 && dmWfRygwdzbList.get(0).getGwjsDm() == 50010001) {
            log.info("该用户只有查询岗权限{}", mobile);
            return null;
        }*/

        loginMap.put("userId", userInfo.getId());
        loginMap.put("name", userInfo.getName());
        loginMap.put("workFlowId", null);
        loginMap.put("workFlowDesc", null);
        loginMap.put("departmentId", userInfo.getDeptId());
        loginMap.put("departmentName", null);


        return loginMap;

    }

    public Boolean updatePwd(String mobile, String pwdOld, String pwdNew) {

        return null;

    }

}
