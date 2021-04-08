package com.example.test.design.model.singleton.lazy;
/**
 * @Description 用内部类的方式实现单例
 * @author leiel
 * @Date 2020/6/11 12:32 PM
 */

public class LazyInnerClassSingleton {

    private LazyInnerClassSingleton() {

        if(LazyHolder.lazy != null) {
            throw new RuntimeException("不允许创建多个实例");
        }

    }


    public static LazyInnerClassSingleton getInstance() {
        return LazyHolder.lazy;
    }

    //内部类的加载机制 调用LazyInnerClassSingleton才会去加载内部类
    public static class LazyHolder {

        private static LazyInnerClassSingleton lazy = new LazyInnerClassSingleton();

    }

}
