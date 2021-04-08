package com.example.test.design.model.factory._抽象工厂;
/**
 * @Description 
 * @author leiel
 * @Date 2020/6/12 9:56 AM
 */

public class SportDriver extends DriverAbstactFactory{
    @Override
    public BenzCar createBenzCar(String car) throws Exception {
        return new BenzSportCar();
    }

    @Override
    public BmwCar createBmwCar(String car) throws Exception {
        return new BmwSportCar();
    }
}
