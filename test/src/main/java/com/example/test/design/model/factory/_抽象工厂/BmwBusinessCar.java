package com.example.test.design.model.factory._抽象工厂;
/**
 * @Description 
 * @author leiel
 * @Date 2020/6/12 9:55 AM
 */

public class BmwBusinessCar extends BmwCar{
    @Override
    public void drive(){
        System.out.println(this.getName()+"----BmwBusinessCar-----------------------");
    }
}
