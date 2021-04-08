package com.example.test.concurrent.blockQueue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/23 1:53 PM
 */
public class BlockQueueDemo {

    ArrayBlockingQueue ab = new ArrayBlockingQueue<>(10, true);
    {
        init();
    }
    public void init() {
        new Thread(()->{

            while(true) {
                try{
                    String data = (String) ab.take();
                    System.out.println("receive:" + data);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    public void addData(String data) throws InterruptedException {
        ab.add(data);
        System.out.println("send:" + data);
        Thread.sleep(1000);
    }

    public static void main(String[] args) throws InterruptedException {

        BlockQueueDemo blockQueueDemo = new BlockQueueDemo();
        for (int i = 0; i < 10; i++) {

            blockQueueDemo.addData("data=" + i);

        }

    }

}
