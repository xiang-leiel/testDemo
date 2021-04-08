package com.example.test.design.model.factory._抽象工厂;
/**
 * @Description 
 * @author leiel
 * @Date 2020/6/12 9:54 AM
 */

public class BmwSportCar extends BmwCar{
    @Override
    public void drive() {
        System.out.println(this.getName() + "----BmwSportCar-----------------------");
    }
}
