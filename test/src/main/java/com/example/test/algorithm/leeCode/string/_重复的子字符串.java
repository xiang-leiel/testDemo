package com.example.test.algorithm.leeCode.string;
/**
 * https://leetcode-cn.com/problems/repeated-substring-pattern/
 * @Description 
 * @author leiel
 * @Date 2020/8/24 11:28 AM
 */

public class _重复的子字符串 {

    /**
     *
     * 给定一个非空的字符串，判断它是否可以由它的一个子串重复多次构成。给定的字符串只含有小写英文字母，并且长度不超过10000。
     * 输入: "abab"
     *
     * 输出: True
     *
     * @param s
     * @return
     */
    public static boolean repeatedSubstringPattern(String s) {

        int n = s.length();
        for (int i = 1; i * 2 <= n; ++i) {
            if (n % i == 0) {
                boolean match = true;
                for (int j = i; j < n; ++j) {
                    if (s.charAt(j) != s.charAt(j - i)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {

        String s = "abcbbabcbbabcbb";

        System.out.println(repeatedSubstringPattern(s));

        System.out.println(s.charAt(4));


    }

}
