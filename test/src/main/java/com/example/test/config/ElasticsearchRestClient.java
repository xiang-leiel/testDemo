package com.example.test.config;

import org.springframework.context.annotation.Configuration;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/10 10:25 PM
 */
@Configuration
public class ElasticsearchRestClient {
/*
    @Value("${elasticsearch.ip1}")
    public String addressip;

    @Bean(name="highLevelClient")
    public RestHighLevelClient highLevelClient() {

        String[] address = addressip.split(":");
        String ip = address[0];
        int port = Integer.valueOf(address[1]);
        HttpHost httpHost = new HttpHost(ip, port, "http");

        return new RestHighLevelClient(RestClient.builder(new HttpHost[]{httpHost}));

    }*/


}
