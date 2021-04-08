package com.example.test.design.model.factory._简单工厂;

import com.example.test.design.model.factory.Benz;
import com.example.test.design.model.factory.Bmw;
import com.example.test.design.model.factory.Car;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/12 9:35 AM
 */

public class DriverFactory {

    public static Car createCar(String car){
        Car c = null;
        if("Benz".equalsIgnoreCase(car))
            c = new Benz();
        else if("Bmw".equalsIgnoreCase(car))
            c = new Bmw();
        return c;
    }

}
