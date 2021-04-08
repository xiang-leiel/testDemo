package com.example.test.design.model.singleton.hungry;

import java.io.Serializable;

/**
 * @Description 饿汉式单例模式
 * @author leiel
 * @Date 2020/6/11 12:02 PM
 */
public class HungrySingleton implements Serializable {

    private static final HungrySingleton  hungrySingleton = new HungrySingleton();

    private HungrySingleton(){}

    public static HungrySingleton getInstance() {

        return hungrySingleton;

    }

    //重写readResolve方法，只不过是覆盖了反序列化出来的对象
    //还是会创建两次，发生在JVM层面，相对来说比较安全
    //之前反序列化出来的对象会被GC回收
    private Object readResolve() {
        return hungrySingleton;
    }

}
