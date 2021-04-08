package com.example.test.algorithm.leeCode.graph;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/interleaving-string/
 * @Description 
 * @author leiel
 * @Date 2020/7/18 10:51 AM
 */
public class _交错字符串 {

    /**
     * 输入: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
     * 输出: true
     * 输入: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
     * 输出: false
     * 利用栈的形势无法解决
     * @param s1
     * @param s2
     * @param s3
     * @return
     */
    public static boolean isInterleave(String s1, String s2, String s3) {
        int n = s1.length(), m = s2.length(), t = s3.length();

        if (n + m != t) {
            return false;
        }

        //默认为false
        boolean[][] f = new boolean[n + 1][m + 1];

        f[0][0] = true;

        for (int i = 0; i <= n; ++i) {
            for (int j = 0; j <= m; ++j) {

                int p = i + j - 1;
                if (i > 0) {
                    f[i][j] = f[i][j] || (f[i - 1][j] && s1.charAt(i - 1) == s3.charAt(p));
                }
                if (j > 0) {
                    f[i][j] = f[i][j] || (f[i][j - 1] && s2.charAt(j - 1) == s3.charAt(p));
                }
            }
        }

        return f[n][m];

    }

    public static void main(String[] args) {

        String s1 = "aabcc";
        String s2 = "dbbca";
        String s3 = "aadbbcbcac";

        //System.out.println(isInterleave(s1, s2, s3));

        int j = 2;
        for(int i = 0; i<10 && j < 5; i++) {

            j++;

        }
    }

}
