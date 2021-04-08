package com.example.test.design.model.decorator;
/**
 * @Description 
 * @author leiel
 * @Date 2020/6/14 2:22 PM
 */

public class BaseBattercake extends Battercake{

    @Override
    protected String getMsg() {
        return "煎饼";
    }

    @Override
    protected int getPrice() {
        return 5;
    }
}
