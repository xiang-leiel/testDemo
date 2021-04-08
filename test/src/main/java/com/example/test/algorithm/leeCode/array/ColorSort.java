package com.example.test.algorithm.leeCode.array;

import com.example.test.utils.SortUtils;

/**
 * https://leetcode-cn.com/problems/sort-colors/
 * @Description 颜色分类  又称荷兰国旗问题
 * @author leiel
 * @Date 2020/5/15 4:57 PM
 */

public class ColorSort {

    /**
     * [2,0,2,1,1,0] 借助3指针实现
     *
     * 1、如果move指的位置是2,则nums[move]与nums[last]交换 last--;
     * 2、如果move指的位置是1,则move++
     * 3、如果move指的位置是0,则nums[move]与nums[first]交换 first++; move++;
     *
     * 当move大于last则循环结束
     * @param nums
     */
    public static void sortColors(int[] nums) {

        int first = 0;

        int last = nums.length - 1;

        int move = 0;

        while(move <= last) {

            if(nums[move] == 0) {

                SortUtils.SwapValue(nums, move++, first++);

            }else if(nums[move] == 1) {

                move++;

            }else{

                SortUtils.SwapValue(nums, move, last--);

            }

        }

        SortUtils.print(nums);
    }

    public static void main(String[] args) {

        int[] nums = {2,0,2,1,1,0};

        sortColors(nums);

    }

}
