package com.example.test.design.model.decorator;
/**
 * @Description 
 * @author leiel
 * @Date 2020/6/14 2:44 PM
 */

public class EggDecorator extends BattercakeDecorator{

    public EggDecorator(Battercake battercake) {
        super(battercake);
    }

    @Override
    protected String getMsg() {
        return super.getMsg()+"加一个鸡蛋";
    }

    @Override
    protected int getPrice() {
        return super.getPrice()+1;
    }
}
