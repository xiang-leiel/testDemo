package com.example.test.test;

import com.example.test.config.AppConfig;
import com.example.test.service.UserInfoService1;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Description 
 * @author leiel
 * @Date 2020/8/5 2:18 PM
 */

public class Test {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        UserInfoService1 userInfoService1 = annotationConfigApplicationContext.getBean(UserInfoService1.class);

        userInfoService1.query();

        System.out.println(testValue());

    }

    public static String testValue () {
        try{
            System.out.println("try");
            int i = 1/0;
        }catch (Exception e){
            System.out.println("0");
            return "0";

        }finally {
            System.out.println("1");
            return "1";
        }
    }

}
