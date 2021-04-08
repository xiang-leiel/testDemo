package com.example.test.controller.test;

import org.springframework.stereotype.Service;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/19 1:58 PM
 */
@Service
public class CarCreditVerify extends CreditVerify{

    @Override
    public <T> void handle(T t) {
        String name = this.getCarCreditContext().getUser().getName();
        System.out.println(name);
    }

}
