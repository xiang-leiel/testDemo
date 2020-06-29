package com.quantchi.tianji.service.search.interfaces.http;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: DeQing-InvestmentEnterprise
 * @description:
 * @author: mf
 * @create: 2019-09-09 14:49
 **/
@CrossOrigin
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 项目查询钉钉页面（权限接口）
     * @return
     */
    @GetMapping("/searchAuthority")
    public ResultInfo searchAuthority(String staffId) {

        ResultInfo resultInfo = userService.searchAuthority(staffId);

        return resultInfo;
    }

    /**
     * 获取该项目所有相关人员
     * @return
     */
    @GetMapping("/getProjectMember")
    public ResultInfo getProjectMember(String visitId) {

        ResultInfo resultInfo = userService.getProjectMember(visitId);

        return resultInfo;
    }

}
