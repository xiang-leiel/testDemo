package com.example.test.design.model.factory._抽象工厂;
/**
 * @Description 
 * @author leiel
 * @Date 2020/6/12 9:53 AM
 */

public class BenzBusinessCar extends BenzCar{

    @Override
    public void drive(){
        System.out.println(this.getName()+"----BenzBusinessCar-----------------------");
    }
}
