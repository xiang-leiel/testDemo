package com.example.test.concurrent.threadpool;
/**
 * @Description 
 * @author leiel
 * @Date 2020/8/28 8:39 PM
 */

public class MyThread implements Runnable{
    @Override
    public void run() {
        System.out.println("哈哈哈哈");
        try {
            int i = 1/0;
        }catch (Exception e) {
            System.out.println("出错了");
            try {
                throw new Exception("报错了");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    }
}
