package com.example.test.reference.autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description 默认是按type 再根据名称
 * @author leiel
 * @Date 2020/6/21 9:47 AM
 */
@Component
public class I {

    @Autowired
    A a;

    public A getA1() {
        return a;
    }
}
