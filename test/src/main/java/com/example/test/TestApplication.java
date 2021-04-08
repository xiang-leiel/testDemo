package com.example.test;

import org.mybatis.spring.annotation.MapperScan;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.example.test.dao")
public class TestApplication {

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(TestApplication.class, args);

        String[] beanNames = ctx.getBeanDefinitionNames();
        for (String str : beanNames) {
            if (str.equals("carCreditContext")) {
                System.out.println(str);
            }
        }

    }

    @Bean
    public Redisson redisson() {
        //设置单机模式
        Config config = new Config();
        config.useSingleServer().setAddress("redis服务地址").setDatabase(0);
        return (Redisson) Redisson.create(config);

        //看门狗的形势，及时添加过期时间    底层使用的是redLock算法
    }
}
