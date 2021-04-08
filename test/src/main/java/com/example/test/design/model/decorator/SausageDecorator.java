package com.example.test.design.model.decorator;
/**
 * @Description 
 * @author leiel
 * @Date 2020/6/14 2:45 PM
 */

public class SausageDecorator extends BattercakeDecorator{

    public SausageDecorator(Battercake battercake) {
        super(battercake);
    }

    @Override
    protected String getMsg() {
        return super.getMsg() + "加一个香肠";
    }

    @Override
    protected int getPrice() {
        return super.getPrice() + 2;
    }
}
