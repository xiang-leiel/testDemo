package com.quantchi.tianji.service.search.interfaces.http;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.aop.annotation.LoginLog;
import com.quantchi.tianji.service.search.enums.ErrCode;
import com.quantchi.tianji.service.search.service.login.LoginService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;
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
     * @param mobile
     * @param passWord
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
     * @param mobile
     * @param pwdOld
     * @param pwdNew
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

    public static void main(String[] args) {

        DocsConfig config = new DocsConfig();

        config.setProjectPath("/Users/leiel/IdeaProjects/project_approve");
        config.setProjectName("project_approve");
        config.setApiVersion("V1.0");
        config.setAutoGenerate(true);
        config.setDocsPath("/Users/leiel/Downloads");
        Docs.buildHtmlDocs(config);

    }

}
