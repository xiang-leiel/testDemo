package com.example.test.algorithm.leeCodeTwo._栈;
/**
 * @Description 
 * @author leiel
 * @Date 2021/5/7 8:09 AM
 */

public class _数组异或操作 {

    public static int xorOperation(int n, int start) {

        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = start + 2 * i;
        }

        int result = nums[0];

        for (int i = 1; i < n; i++) {
            result ^= nums[i];
        }

        return result;

    }

    public static void main(String[] args) {

        System.out.println(xorOperation(5, 0));

    }

}
