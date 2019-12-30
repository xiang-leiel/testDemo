package com.example.test.controller;

import com.example.test.utils.PropertiesUtils;
import com.taobao.diamond.manager.DiamondManager;
import com.taobao.diamond.manager.ManagerListener;
import com.taobao.diamond.manager.impl.DefaultDiamondManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;


/**
 * @Description 
 * @author leiel
 * @Date 2019/12/14 5:11 PM
 */
@RestController
@RequestMapping("/test")
public class testController {

    @GetMapping("/test")
    public String test() {

        String result = PropertiesUtils.getPropValue("server.port");


       DiamondManager manager = new DefaultDiamondManager("1", new ManagerListener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("receive config: " + configInfo);
            }
        });

        return "哇卡卡卡，你好坏啊，我好喜欢啊";
    }

}
