package com.example.test.concurrent.cycliBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/22 12:38 PM
 */

public class ImportFileDataThread extends Thread{

    private CyclicBarrier cyclicBarrier;

    private String fileUrl;

    public ImportFileDataThread(CyclicBarrier cyclicBarrier, String fileUrl) {
        this.cyclicBarrier = cyclicBarrier;
        this.fileUrl = fileUrl;
    }

    @Override
    public void run() {

        System.out.println("开始导入" + fileUrl + "的数据");
        try {
            cyclicBarrier.await();  //阻塞  condition.await
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
