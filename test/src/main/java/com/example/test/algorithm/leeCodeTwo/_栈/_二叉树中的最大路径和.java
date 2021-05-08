package com.example.test.algorithm.leeCodeTwo._栈;
/**
 * @Description 
 * @author leiel
 * @Date 2021/4/27 8:15 AM
 */

public class _二叉树中的最大路径和 {

    int global_max = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {

        dfs(root);
        return global_max;

    }

    public int dfs(TreeNode root) {

        //递归出口
        if (root == null ) {
            return 0;
        }

        int left = dfs(root.left);

        int right = dfs(root.right);

        left = left > 0 ? left : 0;

        right = right > 0 ? right : 0;

        global_max = Math.max(global_max, left + right + root.val);

        return Math.max(left + root.val, right + root.val);

    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

}
