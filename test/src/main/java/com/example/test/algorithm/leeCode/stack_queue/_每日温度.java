package com.example.test.algorithm.leeCode.stack_queue;

import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/daily-temperatures/
 * @Description 
 * @author leiel
 * @Date 2020/6/4 11:20 AM
 */
public class _每日温度 {

    /**
     * 利用栈存放索引
     */
    public static int[] dailyTemperatures(int[] T) {

        if(T == null) {
            return null;
        }

        int[] distanceIndex = new int[T.length];

        int[] result = new int[T.length];

        Stack<Integer> stack = new Stack<>();

        for(int i = 0; i < T.length; i++) {

            if(stack.isEmpty()) {
                stack.push(i);
            }else {
                while(!stack.isEmpty() && T[stack.peek()] < T[i]) {
                    distanceIndex[stack.peek()] = i;
                    stack.pop();
                }

                stack.push(i);
            }

        }

        for(int i = 0; i < T.length; i++) {

            int value = distanceIndex[i] - i;

            value = value < 0 ? 0 : value;
            result[i] = value;

        }

        return result;

    }

    /**
     * 动态规划的思想，倒推
     * 73, 74, 75, 75, 75, 76, 73, 71
     *          3   2   1
     */
    public static int[] dailyTemperaturesTwo(int[] T) {

        if(T == null) {
            return null;
        }
        int[] result = new int[T.length];
        for(int i = T.length-2; i > 0; i--) {
            int j = i + 1;
            while(true) {
                if(T[i] < T[j]) {
                    result[i] = j - i;
                    break;
                } else if(result[j] == 0) {
                    result[i] = 0;
                    break;
                }
                j = result[j] + j;
            }
        }
        return result;

    }

    public static void main(String[] args) {
        int[] nums = {73, 74, 75, 71, 69, 72, 76, 73};
        dailyTemperatures(nums);
    }

}
