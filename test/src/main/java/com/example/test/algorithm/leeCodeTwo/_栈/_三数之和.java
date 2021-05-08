package com.example.test.algorithm.leeCodeTwo._栈;

import java.util.*;

/**
 * @Description 
 * @author leiel
 * @Date 2021/4/19 8:28 AM
 */

public class _三数之和 {

    /**
     * 最先想到的是暴力解法
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum(int[] nums) {

        List<List<Integer>> list = new ArrayList<>();

        Map<String, Integer> map = new HashMap<>();


        Arrays.sort(nums);

        if (nums.length < 2) {
            return list;
        }


        for (int i = 0; i < nums.length; i++) {

            //初始值为
            int startValue = nums[i];

            if (i > 0 && nums[i] == nums[i-1]) {
                continue;
            }

            int left = i+1;
            int right = nums.length-1;

            while (left < right) {

                List<Integer> listSmall = new ArrayList<>();

                if (nums[left] +nums[right] > -startValue) {
                    right--;
                } else if (nums[left] +nums[right] < -startValue) {
                    left++;
                } else {
                    listSmall.add(nums[i]);
                    listSmall.add(nums[left]);
                    listSmall.add(nums[right]);
                    list.add(listSmall);

                    while(left < right && nums[left+1] == nums[left]) {
                        left++;
                    }


                    while(left < right && nums[right-1] == nums[right]) {
                        right--;
                    }

                    left++;
                    right--;
                }

            }
        }


        return list;

    }

    public static void main(String[] args) {

        int[] nums = {0,0,0,0};

        System.out.println(threeSum(nums));



    }

}
