package com.example.test.design.model.factory._抽象工厂;
/**
 * @Description 
 * @author leiel
 * @Date 2020/6/12 9:56 AM
 */

public abstract class DriverAbstactFactory {

    public abstract BenzCar createBenzCar(String car) throws Exception;

    public abstract BmwCar createBmwCar(String car) throws Exception;

}
