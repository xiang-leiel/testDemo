package com.example.test.design.model.factory;
/**
 * @Description 
 * @author leiel
 * @Date 2020/6/12 9:32 AM
 */

public abstract class Car {

    private String name;

    public abstract void drive();

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
