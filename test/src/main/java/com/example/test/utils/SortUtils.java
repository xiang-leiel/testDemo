package com.example.test.utils;

import java.util.Random;

/**
 * @Description 
 * @author leiel
 * @Date 2020/4/9 3:12 PM
 */

public class SortUtils {

    /**
     * 生成随机数组
     * @param
     */
    public static int[] productArr() {
        int[] init = new int[10];
        for(int i = 0; i < 10 ; i++) {
            Random rd = new Random();
            int r = rd.nextInt(100);
            init[i] = r;
        }
        return init;
    }

    /**
     * 数值交换
     * @param newValue
     * @param oldValue
     */
    public static void SwapValue(int[] arr, int newValue, int oldValue) {

        //最小值交换
        int temp = arr[newValue];
        arr[newValue] = arr[oldValue];
        arr[oldValue] = temp;

    }

    /**
     * 打印
     * @param arr
     */
    public static void print(int[] arr) {
        for(int value : arr) {
            System.out.print(value + " ");
        }
    }
}
