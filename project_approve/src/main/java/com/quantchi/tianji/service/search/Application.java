package com.quantchi.tianji.service.search;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * 
 * 搜索服务启动类
 * <p>Title:Application.java</p>
 * <p>Company: quant-chi</p>
 * <p>Description:</p>
 * @author:maxj
 * @date: 2018年10月24日
 *
 */
@SpringBootApplication
@EnableScheduling
@EnableSwagger2
@MapperScan("com.quantchi.tianji.service.search.dao")
@EnableCaching
public class Application {

    public static void main(String[] args) {

        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }
}
