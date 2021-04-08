package com.example.test.design.model.factory._简单工厂;

import com.example.test.design.model.factory.Car;

import java.io.IOException;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/12 9:36 AM
 */

public class SimpleFactoryTest {

    public static void main(String[] args) throws IOException {
        //老板告诉司机我今天坐奔驰
        Car car = DriverFactory.createCar("benz");
        car.setName("benz");
        //司机开着奔驰出发
        car.drive();
    }

}
