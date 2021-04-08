package com.example.test.design.model.singleton.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 容器式单例
 * @author leiel
 * @Date 2020/6/11 5:28 PM
 */
public class ContainerSingleton {

    private ContainerSingleton(){}

    private static Map<String, Object> ioc = new ConcurrentHashMap<>();

    public static Object getBean(String className) {

        //解决并发问题 加synchronized关键字
        synchronized (ioc) {

            if(!ioc.containsKey(className)) {

                Object object = null;

                try{

                    object = Class.forName(className).newInstance();

                    ioc.put(className, object);

                }catch (Exception e) {

                    e.printStackTrace();
                }

                return ioc;
            }
            return ioc.get(className);

        }

    }

}
