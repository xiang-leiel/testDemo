package com.example.test.design.model.factory._工厂方法;

import com.example.test.design.model.factory.Car;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/12 9:40 AM
 */

public abstract class DriverFacory {

    public abstract Car createCar(String car) throws Exception;

}
