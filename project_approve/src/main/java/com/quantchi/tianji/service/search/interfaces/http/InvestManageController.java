package com.quantchi.tianji.service.search.interfaces.http;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.service.project.InvestManageService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description 
 * @author leiel
 * @Date 2020/3/16 10:49 AM
 */
@CrossOrigin
@RestController
@RequestMapping("/invest")
public class InvestManageController {

    @Resource
    private InvestManageService investManageService;

    /**
     * 删除投资方
     * workFlowId 流程状态 userId 用户id
     * @return
     */
    @GetMapping("/delete")
    public ResultInfo delete(String investId) {

        ResultInfo resultInfo = investManageService.delete(investId);

        return resultInfo;
    }
}
