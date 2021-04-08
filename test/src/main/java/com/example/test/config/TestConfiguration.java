package com.example.test.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description 
 * @author leiel
 * @Date 2020/8/7 11:03 AM
 */
@Data
@ConfigurationProperties(prefix = "com.leiel.test")
@Component
public class TestConfiguration {

    private String name;

    private String age;

}
