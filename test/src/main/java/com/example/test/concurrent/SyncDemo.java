package com.example.test.concurrent;


import org.openjdk.jol.info.ClassLayout;


/**
 * @Description JOL(java object layout)
 * @author leiel
 * @Date 2020/1/14 10:23 AM
 */

public class SyncDemo {

    public static void main(String[] args) {

        Object lock = new Object();
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());

        lock.hashCode();
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());

        /**
         * XX:BiasedLockingStartUpDelay=0  jvm启动的时候进入偏向锁模式101 偏向锁->线程ID为0
         * 打开偏向锁，默认就是可偏向对象
         *
         * 如果有线程上锁，上偏向锁指的就是把markword的线程ID改为自己的线程ID
         * 偏向锁不可重偏向，批量偏向
         *
         * 两个线程竞争  撤销偏向锁升级轻量级锁
         * 线程在自己的线程栈生成lockRecord，用CAS操作将markword设置为指向自己这个线程的LR的指针，设置成功者得到锁
         *
         *
         * 如果竞争加聚，轻量级锁升级为重量级锁（有线程自旋超过10次的，或者自旋线程数超过CPU核数的一半的  1.6之后引入自适应自旋 JVM自己控制）
         * 升级重量级锁->向操作系统申请资源，Linux mutex，CPU从3级-0级系统调用，线程挂起，进入等待队列，等待操作系统的调用，然后再映射回用户空间
         *
         */

        synchronized (lock){

            System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        }

    }

}
