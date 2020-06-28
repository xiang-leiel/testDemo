package com.quantchi.tianji.service.search.interfaces.http;

import com.quantchi.core.message.ResultInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 
 * @author leiel
 * @Date 2020/2/6 10:05 PM
 */
@RestController
@RequestMapping("/register")
public class RegisterController {

    /**
     * 用户注册
     * @return
     */
    @GetMapping()
    public ResultInfo preferenceSet(String mobile, String passWord) {

        //MD5加密


        return null;
    }
}
