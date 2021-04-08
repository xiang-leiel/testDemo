package com.example.test.algorithm.leeCode.dynamic;
/**
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/
 * @Description 
 * @author leiel
 * @Date 2020/6/15 8:12 AM
 */

public class _买卖股票的最佳时机 {

    /**
     * 输入: [7,1,5,3,6,4]
     * 输出: 5
     * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {

        int max = 0;

        for (int i = 0; i < prices.length; i++) {

            for (int j = i+1; j < prices.length; j++) {

                int value = prices[j]-prices[i];
                max = Math.max(max, value);

            }

        }

        return max;
    }

    /**
     * [7,1,5,3,6,4]
     * 利用动态规划的思想来做   股票的最佳买卖时机实际上是买卖天之间相邻两天差值之和
     * -6 4 -2 3 -2
     * 第1天卖出的最大利润   dp[1] = -6
     * 第2天卖出的最大利润   dp[2] = 4
     * 第3天卖出的最大利润   dp[3] = 4+(-2) = 2
     * 第4天卖出的最大利润   dp[4] = 2+3 = 5
     * 第5天卖出的最大利润   dp[5] = 5+(-2) = 3
     * @param prices
     * @return
     */
    public static int maxProfitTwo(int[] prices) {

        if(prices == null || prices.length < 2) {
            return 0;
        }

        int[] diffValue = new int[prices.length-1];

        int result = 0;

        for (int i = 0; i < prices.length-1; i++) {

            diffValue[i] = prices[i+1]- prices[i];

        }

        int maxValue = diffValue[0];

        result = maxValue;

        //i为卖出股票的日子
        for (int i = 1; i < diffValue.length; i++) {

            if(maxValue < 0 && diffValue[i] > 0) {
                maxValue = diffValue[i];
            }else{
                maxValue = Math.max(diffValue[i], maxValue + diffValue[i]);
            }
            result = Math.max(result, maxValue);

        }

        return result < 0 ? 0 :result;
    }

    public static void main(String[] args) {
        int[] prices = {1,2};

        System.out.println(maxProfitTwo(prices));
    }

}
