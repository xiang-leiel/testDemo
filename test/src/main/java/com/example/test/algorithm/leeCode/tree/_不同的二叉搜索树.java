package com.example.test.algorithm.leeCode.tree;
/**
 * https://leetcode-cn.com/problems/unique-binary-search-trees/
 * @Description 利用卡特兰数的思想
 * @author leiel
 * @Date 2020/7/15 1:49 PM
 */
public class _不同的二叉搜索树 {

    /**
     * 输入: 3
     * 输出: 5
     * 解释:
     * 给定 n = 3, 一共有 5 种不同结构的二叉搜索树:
     *
     *    1         3     3      2      1
     *     \       /     /      / \      \
     *      3     2     1      1   3      2
     *     /     /       \                 \
     *    2     1         2                 3
     *
     * @param n
     * @return
     */
    public int numTrees(int n) {

        long C = 1;
        for (int i = 0; i < n; ++i) {
            C = C * 2 * (2 * i + 1) / (i + 2);
        }
        return (int) C;

    }

    /**
     * 卡特兰数的思想
     * @param n
     * @return
     */
    public int numTreesTwo(int n) {

        int[] G = new int[n+1];

        G[0] = 1;
        G[1] = 1;

        for (int i = 2; i <= n; ++i) {
            for (int j = 1; j <= i; ++j) {
                G[i] += G[j - 1] * G[i - j];
            }
        }

        return G[n];

    }

}
