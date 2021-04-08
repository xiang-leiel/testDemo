package com.example.test.design.model.singleton.register;
/**
 * @Description 枚举式单例
 * @author leiel
 * @Date 2020/6/11 4:32 PM
 */
public enum EnumSingleton {

    INSTANCE;

    private Object data;


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static EnumSingleton getInstance() {

        return INSTANCE;

    }

}
