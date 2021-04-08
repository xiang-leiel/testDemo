package com.example.test.algorithm.leeCode.array;
/**
 * https://leetcode-cn.com/problems/minimum-size-subarray-sum/
 * @Description 
 * @author leiel
 * @Date 2020/6/28 3:58 PM
 */

public class _长度最小的子数组 {

    /**
     * 输入: s = 7, nums = [2,3,1,2,4,3]
     * 输出: 2
     * 解释: 子数组 [4,3] 是该条件下的长度最小的连续子数组。
     * @param s
     * @param nums
     * @return
     */
    public static int minSubArrayLen(int s, int[] nums) {

        int len = 0;
        int value = 0;

        for (int i = 0; i < nums.length; i++) {

            if(value < s) {
                value += nums[i];
            }
            if(value >= s) {
                len = i + 1;
                break;
            }
        }

        if(len == 0) {
            return 0;
        }


        for (int i = 0; i < nums.length; i++) {

            int index = i+1;
            int max = nums[i];


            while(index < nums.length && max < s) {

                max += nums[index++];

            }

            if(max >= s) {
                len = Math.min(index - i, len);
            }
        }

        return len;

    }

    /**
     * 尝试双指针
     * @param s
     * @param nums
     * @return
     */
    public static int minSubArrayLenTwo(int s, int[] nums) {

        if(nums == null || nums.length == 0) {
            return 0;
        }

        int len = 0;
        int value = 0;

        for (int i = 0; i < nums.length; i++) {

            if(value < s) {
                value += nums[i];
            }
            if(value >= s) {
                len = i + 1;
                break;
            }
        }

        if(len == 0) {
            return 0;
        }

        int li = 0;
        int ri = 0;
        int max = nums[li];

        while (li <= ri && li < nums.length && ri < nums.length) {

            if (max < s) {
                ri++;
                if(ri >= nums.length) {
                    break;
                }
                max += nums[ri];

                if (max >= s) {
                    len = Math.min(ri - li + 1, len);
                    max = max - nums[li];
                    li++;
                }
            }else{
                len = Math.min(ri - li + 1, len);
                max = max - nums[li];
                li++;
            }

        }

        return len;

    }

    public static void main(String[] args) {
        int[] nums = {2,3,1,2,4,3};
        System.out.println(minSubArrayLenTwo(7, nums));
    }

}
