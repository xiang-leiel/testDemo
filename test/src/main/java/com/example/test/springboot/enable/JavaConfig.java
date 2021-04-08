package com.example.test.springboot.enable;

import com.example.test.springboot.other.OtherData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Description 
 * @author leiel
 * @Date 2020/7/3 8:45 PM
 */
@Configuration
@Import(value = OtherData.class)
public class JavaConfig {

    @Bean
    public TestData testData() {

        System.out.println("Javaconfig 自动装配");

        return new TestData();

    }

}
