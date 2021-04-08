package com.example.test.algorithm.leeCode.stack_queue;

import java.util.Deque;
import java.util.LinkedList;

/**
 * https://leetcode-cn.com/problems/sliding-window-maximum/
 * @Description
 * @author leiel
 * @Date 2020/5/26 9:00 AM
 */

public class _滑动窗口最大值 {


    //使用双端队列
    public static int[] maxSlidingWindow(int[] nums, int k) {

        int[] result = new int[nums.length - k + 1];

        if(nums == null || k < 1) {return null;}

        if(k == 1) {
            return nums;
        }

        //双端队列
        Deque<Integer> deque = new LinkedList<>();

        //窗口头指针--数组的第一个位置
        int head = 0;

        //窗口尾指针--0-k
        int tail = 0-k+1;

        //尾指针从数组的第一个位置开始，窗口才开始滑动
        while(tail <= nums.length - k + 1 && head < nums.length) {

            //取最大值保证队列降序  取最小值则相反
            if(deque.isEmpty()) {
                deque.addLast(head);
            }else {
                //取出队尾元素和当前值比较 如果比他大则将下标存到队列中，否则，弹出前面的队尾
                int last = deque.getLast();
                while (nums[last] < nums[head]) {
                    deque.removeLast();
                    if(deque.isEmpty()) {
                        break;
                    }else {
                        last = deque.getLast();
                    }
                }

                deque.add(head);

                //过期的下标也要去掉
                int first = deque.getFirst();
                while(first < tail) {
                    deque.removeFirst();
                    if(deque.isEmpty()) {
                        break;
                    }else {
                        first = deque.getFirst();
                    }
                }

                if(tail >= 0) {
                    result[tail] = nums[deque.peekFirst()];
                }
            }

            head++;
            tail++;

        }

        return result;
    }

    //不使用双端队列 将最新的最大值存放在数组
    public static int[] maxSlidingWindow_two(int[] nums, int k) {

        int[] result = new int[nums.length - k + 1];

        if(nums == null || k < 1) {return null;}

        if(k == 1) {
            return nums;
        }

        int leftIndex = 0;

        int rightIndex = k-1;

        int maxIndex = leftIndex;

        //获取初始的最大值索引
        maxIndex = getMaxIndex(nums, leftIndex, rightIndex);

        //扫描获取区间内最大值
        while(rightIndex <= nums.length-1) {

            //先查看maxIndex是否在区间范围内
            if(maxIndex < leftIndex || maxIndex > rightIndex){
                maxIndex = getMaxIndex(nums, leftIndex, rightIndex);
            }

            if(nums[rightIndex] > nums[maxIndex]) {
                maxIndex = rightIndex;
            }

            result[leftIndex] = nums[maxIndex];
            rightIndex++;
            leftIndex++;

        }

        return result;
    }

    private static int getMaxIndex(int[] nums, int leftIndex, int rightIndex) {

        int max = leftIndex;

        while(leftIndex <= rightIndex) {

            if(nums[leftIndex] > nums[max]) {
                max= leftIndex;
            }
            leftIndex++;

        }

        return max;
    }


    public static void main(String[] args) {

        int[] nums = {9,10,9,-7,-4,-8,2,-6};
        maxSlidingWindow_two(nums, 5);
    }
}
