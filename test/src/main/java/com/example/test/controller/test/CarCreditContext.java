package com.example.test.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/19 1:57 PM
 */
@Service
@Component
public class CarCreditContext {

    @Resource
    private CarAuthVerify carAuthVerify;

    @Resource
    private CarCreditVerify carCreditVerify;

    /**状态处理类*/
    private CreditVerify creditVerify;

    private User user;

    public void handle(){
        this.creditVerify = this.getCarAuthVerify();
        this.creditVerify.handle(null);
    }

    public CarAuthVerify getCarAuthVerify() {
        return getCreditVerify(carAuthVerify);
    }

    public void setCarAuthVerify(CarAuthVerify carAuthVerify) {
        this.carAuthVerify = carAuthVerify;
    }

    public CarCreditVerify getCarCreditVerify() {
        return getCreditVerify(carCreditVerify);
    }

    public void setCarCreditVerify(CarCreditVerify carCreditVerify) {
        this.carCreditVerify = carCreditVerify;
    }

    public CreditVerify getCreditVerify() {
        return getCreditVerify(creditVerify);
    }

    public void setCreditVerify(CreditVerify creditVerify) {
        this.creditVerify = creditVerify;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private <T extends CreditVerify> T getCreditVerify(T CreditVerify){
        if (CreditVerify.getCarCreditContext() == null) {
            synchronized(CreditVerify){
                if (CreditVerify.getCarCreditContext() == null) {
                    CreditVerify.setCarCreditContext(this);
                }
            }
        }
        return CreditVerify;
    }

}
