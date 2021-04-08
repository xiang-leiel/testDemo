package com.example.test.controller.test;

import com.example.test.controller.test.CarCreditContext;
import org.springframework.stereotype.Service;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/19 1:58 PM
 */
@Service
public abstract class CreditVerify {

    protected CarCreditContext carCreditContext;

    public CarCreditContext getCarCreditContext() {
        return carCreditContext;
    }

    public void setCarCreditContext(CarCreditContext carCreditContext) {
        this.carCreditContext = carCreditContext;
    }

    public abstract <T> void handle(T t);

}
