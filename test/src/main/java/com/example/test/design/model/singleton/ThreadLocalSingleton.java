package com.example.test.design.model.singleton;
/**
 * @Description 基于threadLocal  并非全局的线程安全，是线程间的线程安全 多线程创建多个实例  伪线程安全
 * @author leiel
 * @Date 2020/6/11 7:22 PM
 */
public class ThreadLocalSingleton {

    private ThreadLocalSingleton(){}

    private static final ThreadLocal<ThreadLocalSingleton> threadLocalSingleton =
            new ThreadLocal<ThreadLocalSingleton>() {
                @Override
                protected ThreadLocalSingleton initialValue() {
                    return new ThreadLocalSingleton();
                }
            };

    public static ThreadLocalSingleton getInstance() {
        return threadLocalSingleton.get();
    }

}
