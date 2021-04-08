package com.example.test.algorithm.leeCode.string;
/**
 * https://leetcode-cn.com/problems/palindromic-substrings/
 * @Description 
 * @author leiel
 * @Date 2020/8/19 10:59 AM
 */

public class _回文子串 {

    public int countSubstrings(String s) {
        int n = s.length(), ans = 0;
        for (int i = 0; i < 2 * n - 1; ++i) {
            int l = i / 2, r = i / 2 + i % 2;
            while (l >= 0 && r < n && s.charAt(l) == s.charAt(r)) {
                --l;
                ++r;
                ++ans;
            }
        }
        return ans;
    }

}
