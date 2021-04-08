package com.example.test.reference.resource;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description Resource 默认是按name 再根据type
 * @author leiel
 * @Date 2020/6/21 9:59 AM
 */
@Component
public class U {

    @Resource
    B b;

    public B getB1() {
        return b;
    }
}
