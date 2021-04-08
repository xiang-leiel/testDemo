package com.example.test.concurrent.cycliBarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/22 12:37 PM
 */

public class CycliBarrierDemo extends Thread{

    @Override
    public void run() {
        System.out.println("开始进行实时计算");
    }

    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new CycliBarrierDemo());

        new Thread(new ImportFileDataThread(cyclicBarrier, "file1")).start();
        new Thread(new ImportFileDataThread(cyclicBarrier, "file2")).start();
        new Thread(new ImportFileDataThread(cyclicBarrier, "file3")).start();

    }

}
