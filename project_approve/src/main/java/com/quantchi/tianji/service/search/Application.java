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

    public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.quantchi.tianji";

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).pathMapping("")
            .apiInfo(apiInfo()).select().apis(RequestHandlerSelectors
            .basePackage(SWAGGER_SCAN_BASE_PACKAGE)).paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("量知产业知识中心")
                .description("首页,搜索,关键字模块Api文档").termsOfServiceUrl("http://mydomain/").contact("黄伟").version("1.0").build();
    }
}
