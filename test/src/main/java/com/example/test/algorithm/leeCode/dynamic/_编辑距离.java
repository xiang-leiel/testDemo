package com.example.test.algorithm.leeCode.dynamic;
/**
 * https://leetcode-cn.com/problems/edit-distance/
 * @Description 动态规划的状态转移方程
 * @author leiel
 * @Date 2020/6/15 10:34 AM
 */

public class _编辑距离 {

    /**
     * 输入：word1 = "horse", word2 = "ros"
     * 输出：3
     * 解释：
     * horse -> rorse (将 'h' 替换为 'r')
     * rorse -> rose (删除 'r')
     * rose -> ros (删除 'e')
     *
     * 定义一个二维数组 用来存放从Word1到word2最少次数
     *
     * dp[i][j]是由s1[0,i)转换到s2[0,j]的最少操作数
     * 可以分4种情况
     * 1、删除s1[0,i)的一个元素 dp[i][j] = dp[i-1][j] + 1;
     * 2、由s1[0,i)转换到s2[0,j-1) dp[i][j] = dp[i][j-1] + 1;
     * 3、如果s1[i-1] != s2[j-1] 则dp[i][j] = dp[i-1][j-1];
     * @param word1
     * @param word2
     * @return
     */
    public static int minDistance(String word1, String word2) {

        //加1的原因是用来存放初始串转化，假设word1是空，转成word2
        int[][] dp = new int[word1.length()+1][word2.length()+1];

        dp[0][0] = 0;

        for(int i = 1; i < word1.length()+1; i++) {
            dp[i][0] = i;
        }

        for(int i = 1; i < word2.length()+1; i++) {
            dp[0][i] = i;
        }

        for(int i = 1; i < word1.length()+1; i++) {

            for(int j = 1; j < word2.length()+1; j++) {
                int min = 0;
                if(word1.charAt(i-1) == word2.charAt(j-1)) {
                    min = dp[i-1][j-1];
                }else {
                    min = dp[i-1][j-1] + 1;
                }
                dp[i][j] = Math.min(Math.min(dp[i][j-1] + 1, dp[i-1][j] + 1), min);
            }
        }

        return dp[word1.length()][word2.length()];

    }

    public static void main(String[] args) {

        String word1= "horse";

        String word2 = "ros";

        System.out.println(minDistance(word1, word2));

    }

}
