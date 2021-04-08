package com.example.test.design.model.factory._工厂方法;

import com.example.test.design.model.factory.Benz;
import com.example.test.design.model.factory.Car;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/12 9:42 AM
 */

public class BenzDriver extends DriverFacory {
    @Override
    public Car createCar(String car) throws Exception {
        return new Benz();
    }
}
