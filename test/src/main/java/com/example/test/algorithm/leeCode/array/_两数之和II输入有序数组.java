package com.example.test.algorithm.leeCode.array;

/**
 * https://leetcode-cn.com/problems/two-sum-ii-input-array-is-sorted/
 * @Description 
 * @author leiel
 * @Date 2020/7/20 10:03 PM
 */
public class _两数之和II输入有序数组 {

    /**
     * 输入: numbers = [2, 7, 11, 15], target = 9
     * 输出: [1,2]
     * 解释: 2 与 7 之和等于目标数 9 。因此 index1 = 1, index2 = 2
     *
     * @param numbers
     * @param target
     * @return
     */
    public static int[] twoSum(int[] numbers, int target) {

        int[] result = new int[2];

        for(int i = 0; i < numbers.length; i++) {

            for(int j = i+1; j < numbers.length; j++) {

                if(numbers[i] + numbers[j] > target) {
                    break;
                }

                if(numbers[i] + numbers[j] == target) {
                    result[0] = i+1;
                    result[1] = j+1;
                }

            }

        }

        return result;

    }

    public static void main(String[] args) {

        int[] numbers = {2, 7, 11, 15};

        System.out.println(twoSum(numbers, 9));

    }

}
