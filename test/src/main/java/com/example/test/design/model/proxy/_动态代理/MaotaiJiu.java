package com.example.test.design.model.proxy._动态代理;
/**
 * @Description 
 * @author leiel
 * @Date 2020/6/12 10:18 AM
 */

public class MaotaiJiu implements SellWine{
    @Override
    public void maiJiu() {
        System.out.println("卖的是茅台酒");
    }
}
