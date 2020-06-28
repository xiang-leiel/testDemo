package com.quantchi.tianji.service.search.service.login;

import com.quantchi.tianji.service.search.dao.*;
import com.quantchi.tianji.service.search.model.*;
import com.quantchi.tianji.service.search.service.department.DepartmentService;
import com.quantchi.tianji.service.search.utils.JwtUtils;
import com.quantchi.tianji.service.search.utils.MD5Utils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 
 * @author leiel
 * @Date 2020/2/6 10:20 PM
 */
@Slf4j
@Service
public class LoginService {

    @Resource
    private DmCsUserMapper dmCsUserMapper;

    @Resource
    private DmWfRygwdzbMapper dmWfRygwdzbMapper;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private DmCsGwjsMapper dmCsGwjsMapper;

    @Value("jwt.secret")
    private String secret;

    public Map<String, Object> checkMobile(String mobile, String passWord){

        Map<String, Object> loginMap = new HashMap<>();

        //查询用户基本信息表获取用户是否录入
        DmCsUser dmCsUser = dmCsUserMapper.selectBymobile(mobile);

        String md5Passwd = MD5Utils.encode(passWord);

        if(dmCsUser == null || (!dmCsUser.getDlMm().equals(passWord) && !dmCsUser.getDlMm().equals(md5Passwd))) {
            log.info("用户基本信息未初始化", mobile);
            return null;
        }
        //获取人员岗位关系代码
        List<DmWfRygwdzb> dmWfRygwdzbList = dmWfRygwdzbMapper.selectByUserDm(dmCsUser.getUserDm());
        if(CollectionUtils.isEmpty(dmWfRygwdzbList)) {
            log.info("用户人员岗位关系未设置{}", mobile);
            return null;
        }
        if(dmWfRygwdzbList.size() == 1 && dmWfRygwdzbList.get(0).getGwjsDm() == 50010001) {
            log.info("该用户只有查询岗权限{}", mobile);
            return null;
        }

        List<String> workFlows = new ArrayList<>();

        String workFlowDesc = "商务局研判岗";
        for(DmWfRygwdzb dmWfRygwdzb : dmWfRygwdzbList) {
            workFlows.add(dmWfRygwdzb.getGwjsDm().toString());
            DmCsGwjs dmCsGwjs = dmCsGwjsMapper.selectByPrimaryKey(dmWfRygwdzb.getGwjsDm());
            if(dmCsGwjs.getGwjsDm() == 10010002 || dmCsGwjs.getGwjsDm() == 10010003
                    || dmCsGwjs.getGwjsDm() == 10010004
                    || dmCsGwjs.getGwjsDm() == 10010001) {
                workFlowDesc = dmCsGwjs.getGwjsmc();
            }
        }

        DmCsDept deptInfo = departmentService.selectDeptInfo(dmCsUser.getDeptDm());

        loginMap.put("userId", dmCsUser.getUserDm().toString());
        loginMap.put("name", dmCsUser.getXm());
        loginMap.put("workFlowId", workFlows);
        loginMap.put("workFlowDesc", workFlowDesc);
        loginMap.put("departmentId", dmCsUser.getDeptDm().toString());
        loginMap.put("departmentName", deptInfo.getBmmc());

        //获取token
        String token = JwtUtils.sign(dmCsUser.getUserDm().toString(), "deqingswjweb");
        loginMap.put("token", token);

        return loginMap;
    }


    public Boolean updatePwd(String mobile, String pwdOld, String pwdNew) {

        //校验老密码是否正确
        DmCsUser dmCsUser = dmCsUserMapper.selectBymobile(mobile);

        String enCodeOld = MD5Utils.encode(pwdOld);
        if(!enCodeOld.equals(dmCsUser.getDlMm()) && !pwdOld.equals(dmCsUser.getDlMm())) {
            return false;
        }

        //设置新密码 MD5加密
        String enCodeNew = MD5Utils.encode(pwdNew);

        DmCsUser dmCsUser1 = new DmCsUser();
        dmCsUser1.setUserDm(dmCsUser.getUserDm());
        dmCsUser1.setDlMm(enCodeNew);
        dmCsUserMapper.updateByPrimaryKeySelective(dmCsUser1);

        return true;
    }
}
