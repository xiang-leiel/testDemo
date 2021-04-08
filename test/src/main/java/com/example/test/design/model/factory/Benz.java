package com.example.test.design.model.factory;

import com.example.test.design.model.factory.Car;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/12 9:33 AM
 */
public class Benz extends Car {

    @Override
    public void drive(){
        System.out.println(this.getName()+"----go-----------------------");
    }
}
