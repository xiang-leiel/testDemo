package com.example.test.algorithm.leeCode.tree;
/**
 * https://leetcode-cn.com/problems/house-robber-iii/
 * @Description 
 * @author leiel
 * @Date 2020/8/5 10:33 AM
 */

public class _打家劫舍III {

    /**
     * 输入: [3,2,3,null,3,null,1]
     *
     *      3
     *     / \
     *    2   3
     *     \   \
     *      3   1
     *
     * 输出: 7
     * 解释: 小偷一晚能够盗取的最高金额 = 3 + 3 + 1 = 7.
     *
     * @param root
     * @return
     */
    public static int rob(TreeNode root) {

        int[] rootStatus = dfs(root);
        return Math.max(rootStatus[0], rootStatus[1]);
    }

    public static int[] dfs(TreeNode node) {
        if (node == null) {
            return new int[]{0, 0};
        }
        int[] l = dfs(node.left);
        int[] r = dfs(node.right);

        //当前节点偷 当前节点的值加上左右节点不偷的值
        int selected = node.val + l[1] + r[1];

        //当前节点不偷 左右节点的偷的值相加
        int notSelected = Math.max(l[0], l[1]) + Math.max(r[0], r[1]);
        return new int[]{selected, notSelected};
    }

    public static void main(String[] args) {
        TreeNode node = new TreeNode(3);
        node.left = new TreeNode(2);
        node.left.right = new TreeNode(3);
        node.right = new TreeNode(3);
        node.right.right = new TreeNode(1);

        System.out.println(rob(node));


    }

}
