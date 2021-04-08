package com.example.test.algorithm.leeCode.dynamic;
/**
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/
 * @Description 
 * @author leiel
 * @Date 2020/7/10 1:32 PM
 */

public class _最佳买卖股票时机含冷冻期 {

    /**
     * 输入: [1,2,3,0,2]
     * 输出: 3
     * 解释: 对应的交易状态为: [买入, 卖出, 冷冻期, 买入, 卖出]
     *
     * 解题思路:要分为三种情况
     *  1、 手上持有股票的最大收益
     *  2、 手上不持有股票，并且处于冷冻期中的累计最大收益
     *  3、 手上不持有股票，并且不在冷冻期中的累计最大收益
     *  分析就从i-1的地方分析，来根据三种情况，买入卖出等操作
     * @param prices
     * @return
     */
    public static int maxProfit(int[] prices) {

        if (prices.length == 0) {
            return 0;
        }

        int n = prices.length;
        // f[i][0]: 手上持有股票的最大收益

        // f[i][1]: 手上不持有股票，并且处于冷冻期中的累计最大收益

        // f[i][2]: 手上不持有股票，并且不在冷冻期中的累计最大收益
        int[][] f = new int[n][3];
        f[0][0] = -prices[0];
        for (int i = 1; i < n; ++i) {
            f[i][0] = Math.max(f[i - 1][0], f[i - 1][2] - prices[i]);
            f[i][1] = f[i - 1][0] + prices[i];
            f[i][2] = Math.max(f[i - 1][1], f[i - 1][2]);
        }
        return Math.max(f[n - 1][1], f[n - 1][2]);

    }

    public static void main(String[] args) {

        int[] nums = {1,2,3,0,2};

        System.out.println(maxProfit(nums));

    }

}
