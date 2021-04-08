package com.example.test.design.model.factory._工厂方法;

import com.example.test.design.model.factory.Car;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/12 9:46 AM
 */

public class FactoryMethodTest {

    public static void main(String[] args) throws Exception {
        DriverFacory d = new BenzDriver();
        Car c = d.createCar("benz");
        c.setName("benz");
        c.drive();
    }

}
