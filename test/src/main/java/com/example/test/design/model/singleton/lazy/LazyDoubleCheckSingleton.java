package com.example.test.design.model.singleton.lazy;
/**
 * @Description 
 * @author leiel
 * @Date 2020/6/11 12:25 PM
 */

public class LazyDoubleCheckSingleton {

    private static LazyDoubleCheckSingleton lazySingleton = null;

    private LazyDoubleCheckSingleton(){}

    public static LazyDoubleCheckSingleton getInstance() {

        if(lazySingleton == null) {

            synchronized (LazyDoubleCheckSingleton.class) {
                if(lazySingleton == null) {
                    lazySingleton = new LazyDoubleCheckSingleton();
                }
            }

        }
        return lazySingleton;
    }
}
