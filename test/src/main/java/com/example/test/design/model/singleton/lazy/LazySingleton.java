package com.example.test.design.model.singleton.lazy;
/**
 * @Description 懒汉式单例模式
 * @author leiel
 * @Date 2020/6/11 12:05 PM
 */
public class LazySingleton {

    private static LazySingleton lazySingleton = null;

    private LazySingleton(){}

    public synchronized static LazySingleton getInstance() {

        if(lazySingleton == null) {
            lazySingleton = new LazySingleton();
        }
        return lazySingleton;
    }

}
