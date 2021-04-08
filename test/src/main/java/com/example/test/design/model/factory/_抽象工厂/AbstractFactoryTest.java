package com.example.test.design.model.factory._抽象工厂;
/**
 * @Description 
 * @author leiel
 * @Date 2020/6/12 9:58 AM
 */

public class AbstractFactoryTest {

    public static void main(String[] args) throws Exception {

        DriverAbstactFactory factory = new SportDriver();

        BmwCar bmw = factory.createBmwCar("bmw");

        bmw.setName("bmw");

        bmw.drive();

    }

}
