package com.example.test.algorithm.leeCode.string;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/count-binary-substrings/
 * @Description 
 * @author leiel
 * @Date 2020/8/10 5:02 PM
 */
public class _计数二进制子串 {

    /**
     * 输入: "00110011"
     * 输出: 6
     * 解释: 有6个子串具有相同数量的连续1和0：“0011”，“01”，“1100”，“10”，“0011” 和 “01”。
     *
     * @param s
     * @return
     */
    public static int countBinarySubstrings(String s) {

        List<Integer> counts = new ArrayList<Integer>();
        int ptr = 0, n = s.length();

        while (ptr < n) {
            char c = s.charAt(ptr);
            int count = 0;
            while (ptr < n && s.charAt(ptr) == c) {
                ++ptr;
                ++count;
            }
            counts.add(count);
        }

        int ans = 0;
        for (int i = 1; i < counts.size(); ++i) {
            ans += Math.min(counts.get(i), counts.get(i - 1));
        }

        return ans;

    }

    public static void main(String[] args) {
        countBinarySubstrings("00111011");
    }

}
