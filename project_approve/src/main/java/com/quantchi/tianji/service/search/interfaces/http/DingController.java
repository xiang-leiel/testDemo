package com.quantchi.tianji.service.search.interfaces.http;

import com.dingtalk.api.response.OapiUserGetResponse;
import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.exception.TianjiErrorCode;
import com.quantchi.tianji.service.search.service.DingService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: DeQing-InvestmentEnterprise
 * @description: 用于获取钉钉数据信息的controller
 * @author: mf
 * @create: 2019-07-12 16:05
 **/
@RestController
@RequestMapping("/ding")
public class DingController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DingService dingService;


    @GetMapping("/userid/get")
    public ResultInfo getUserid(String accessToken, String code) {
        String userid = "";
        try {
            userid = dingService.getUserid(accessToken,code);
        } catch (Exception e) {
            logger.error("调用钉钉接口出错,异常信息:{}", e.getMessage());
            return ResultUtils.fail(TianjiErrorCode.DING_ERROR);
        }
        return ResultUtils.success(userid);
    }

    @GetMapping("/user/detail")
    public ResultInfo getUserDetail(String accessToken, String staffId) {
        OapiUserGetResponse userDetail = null;
        try {
            userDetail = dingService.getUserDetail(accessToken, staffId);
        } catch (Exception e) {
            logger.error("调用钉钉接口出错,异常信息:{}", e.getMessage());
            return ResultUtils.fail(TianjiErrorCode.DING_ERROR);
        }
        return ResultUtils.success(userDetail);
    }

    @GetMapping("/sychronize")
//    @Scheduled(cron = "0 0 2 * * ?")
    public ResultInfo<String> sychronizeData() {
        try {
            logger.info("------------------定时任务开启,开始同步钉钉用户数据---------------------");
            dingService.sychronizeDataToMysql();
        } catch (Throwable e) {
            logger.error("------------------定时任务失败:" + e.getMessage() + "---------------------");
            return ResultUtils.success(e.getMessage());
        }
        logger.info("------------------定时任务完成,同步钉钉用户数据成功---------------------");
        return ResultUtils.success("success");
    }

    @GetMapping("/token/get")
    public ResultInfo getToken() {
        String token = "";
        try {
            token = dingService.getToken();
        } catch (Exception e) {
            logger.error("调用钉钉接口出错,异常信息:{}", e.getMessage());
            return ResultUtils.fail(TianjiErrorCode.DING_ERROR);
        }
        return ResultUtils.success(token);
    }
}
