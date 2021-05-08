package com.example.test.algorithm.leeCodeTwo._栈;

import org.redisson.misc.Hash;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 
 * @author leiel
 * @Date 2021/4/15 8:07 AM
 */

public class _只出现一次的数字 {

    public static int singleNumber(int[] nums) {

        Map<Integer, Integer> map = new HashMap<>();

        Integer min = Integer.MIN_VALUE;

        for (int a : nums) {
            if (!map.isEmpty() && map.keySet().contains(a)) {
                 map.put(a, map.get(a)+1);
            }else {
                map.put(a,1);
            }
        }

        for (Integer value : map.keySet()) {
            if (map.get(value) == 1) {
                min = value;
            }
        }

        return min;

    }

    public static int singleNumberTwo(int[] nums) {

        int startValue = 0;

        for (int a : nums) {
            startValue ^= a;
        }

        return startValue;
    }

    public static void main(String[] args) {

        int[] nums = {2,3,2,3,1};

        System.out.println(singleNumberTwo(nums));

    }

}
