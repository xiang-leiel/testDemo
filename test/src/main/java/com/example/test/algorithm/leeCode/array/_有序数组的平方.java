package com.example.test.algorithm.leeCode.array;

import com.example.test.utils.SortUtils;

/**
 * @Description 有序数组的平方
 * @author leiel
 * @Date 2020/5/19 8:44 AM
 */
public class _有序数组的平方 {

    //输入：[-4,-1,0,3,10]
    //输出：[0,1,9,16,100]

    public static int[] sortedSquares(int[] A) {

        int[] newArray = new int[A.length];

        //遍历第一遍进行平方运算 记录最后一个小于0的位置
        for(int i = 0; i < A.length; i++) {
            newArray[i] = A[i]*A[i];
        }

        //[16,1,0,9,100]  双指针走起, 一头一尾对比无敌
        int[] resultArray = new int[A.length];

        int head = 0;

        int tail = A.length-1;

        for(int i = A.length-1; i >= 0; i--) {

            if(newArray[head] >= newArray[tail]) {
                resultArray[i] = newArray[head++];
            } else {
                resultArray[i] = newArray[tail--];
            }

        }

        SortUtils.print(resultArray);

        return resultArray;

    }

    public static void main(String[] args) {

        int[] A = {-4,-1,0,3,10};

        sortedSquares(A);

    }

}
