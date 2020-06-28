package com.quantchi.tianji.service.search.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * @program: DeQing-InvestmentEnterprise
 * @description: 文件上传配置
 * @author: mf
 * @create: 2019-07-11 15:41
 **/
@Configuration
public class TomcatConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize("10MB"); // KB,MB
        /// 总上传数据大小
        factory.setMaxRequestSize("10MB");
        return factory.createMultipartConfig();
    }
}
