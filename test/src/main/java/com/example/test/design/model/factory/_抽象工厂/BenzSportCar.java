package com.example.test.design.model.factory._抽象工厂;
/**
 * @Description 
 * @author leiel
 * @Date 2020/6/12 9:52 AM
 */

public class BenzSportCar extends BenzCar {

    @Override
    public void drive() {
        System.out.println(this.getName() + "----BenzSportCar-----------------------");
    }
}
