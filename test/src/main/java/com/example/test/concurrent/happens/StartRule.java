package com.example.test.concurrent.happens;
/**
 * @Description 
 * @author leiel
 * @Date 2020/6/17 8:33 AM
 */

public class StartRule {

    static int x = 0;

    /**
     * 主线程happens-before子线程 主线程修改数据对子线程可见
     * @param args
     */
    public static void main(String[] args) {

        Thread thread = new Thread(()->{
            System.out.println(x);
        });

        x=10;

        thread.start();

    }

}
