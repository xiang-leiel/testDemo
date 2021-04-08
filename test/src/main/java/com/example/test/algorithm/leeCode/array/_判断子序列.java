package com.example.test.algorithm.leeCode.array;
/**
 * https://leetcode-cn.com/problems/is-subsequence/
 * @Description 
 * @author leiel
 * @Date 2020/7/27 5:29 PM
 */

public class _判断子序列 {

    /**
     * 示例 1:
     * s = "abc", t = "ahbgdc"
     *
     * 返回 true.
     *
     * 示例 2:
     * s = "axc", t = "ahbgdc"
     *
     * 返回 false
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isSubsequence(String s, String t) {

        char[] charS = s.toCharArray();

        char[] charT = t.toCharArray();

        int index = 0;
        int count = 0;
        for(int i = 0; i < charS.length; i++) {

            while(index < charT.length) {

                if(charS[i] == charT[index]) {
                    count++;
                    index++;
                    break;
                }
                index++;

            }

        }

        if(count == charS.length) {
            return true;
        }

        return false;

    }

    public static void main(String[] args) {

        String s = "abc";

        String t = "ahbgdc";

        System.out.println(isSubsequence(s, t));

    }

}
