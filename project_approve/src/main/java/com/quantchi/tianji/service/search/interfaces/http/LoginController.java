package com.quantchi.tianji.service.search.interfaces.http;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.aop.annotation.LoginLog;
import com.quantchi.tianji.service.search.enums.ErrCode;
import com.quantchi.tianji.service.search.service.login.LoginService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description 
 * @author leiel
 * @Date 2020/2/6 6:59 PM
 */
@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private LoginService loginService;

    /**
     * 用户登录
     * @return
     */
    @LoginLog(name = "记录用户登录日志")
    @GetMapping()
    public ResultInfo login(String mobile, String passWord) {

         Map<String,Object> loginResult = loginService.checkMobile(mobile, passWord);

        if(null == loginResult) {
            return ResultUtils.fail(ErrCode.NOT_EXIST.getCode(),"您输入的手机号或密码错误，请重新输入");
        }

        return ResultUtils.success(loginResult);
    }

    /**
     * 修改密码
     * @return
     */
    @GetMapping("/updatePwd")
    public ResultInfo updatePassword(String mobile, String pwdOld, String pwdNew) {

        if(pwdOld.equals(pwdNew)){
            return ResultUtils.fail(ErrCode.PASSWORD_SAME);
        }

        Boolean updateFlag = loginService.updatePwd(mobile, pwdOld, pwdNew);

        if(!updateFlag) {
            return ResultUtils.fail(ErrCode.PASSWORD_FAIL);
        }

        return ResultUtils.success("success");
    }

}
