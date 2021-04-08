package com.example.test.algorithm.leeCode.array;
/**
 * https://leetcode-cn.com/problems/magic-index-lcci/
 * @Description 
 * @author leiel
 * @Date 2020/7/31 10:37 AM
 */

public class _魔术索引 {

    /**
     * 示例1:
     *
     *  输入：nums = [0, 2, 3, 4, 5]
     *  输出：0
     *  说明: 0下标的元素为0
     * 示例2:
     *
     *  输入：nums = [1, 1, 1]
     *  输出：1
     *
     * @param nums
     * @return
     */
    public int findMagicIndex(int[] nums) {

        for (int i = 0; i < nums.length; i++) {

            if(nums[i] > i) {
                break;
            }

            if(nums[i] == i){
                return i;
            }

        }

        return -1;

    }

}
