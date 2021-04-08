package com.example.test.springboot.importselect;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description 
 * @author leiel
 * @Date 2020/7/4 3:26 PM
 */
@ConfigurationProperties("com.example.test")
public class TestProperities {

    private String host;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
