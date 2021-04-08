package com.example.test.design.model.factory._抽象工厂;
/**
 * @Description 
 * @author leiel
 * @Date 2020/6/12 9:53 AM
 */

public abstract class BmwCar {

    private String name;

    public abstract void drive();

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
