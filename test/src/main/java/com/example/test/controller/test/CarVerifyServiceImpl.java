package com.example.test.controller.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/19 2:13 PM
 */
@Slf4j
@Service
public class CarVerifyServiceImpl implements CarVerifyService {

    @Autowired
    private CarCreditContext carCreditContext;

    @Override
    public void verify(User user) {

        carCreditContext.setUser(user);

        carCreditContext.handle();

    }

}
