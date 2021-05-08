package com.example.test.algorithm.leeCodeTwo._栈;
/**
 * @Description 
 * @author leiel
 * @Date 2021/4/26 8:10 AM
 */

public class _求根节点到叶节点数字之和 {

    int sum = 0;

    /**
     * 使用深度优先搜索 dfs
     * @param root
     * @return
     */
    public int sumNumbers(TreeNode root) {

        if (root == null) {
            return 0;
        }

        dfs(root, 0);

        return sum;

    }

    public void dfs(TreeNode root, int num) {

        num = num * 10 + root.val;
        if (root.left == null && root.right == null) {
            sum += num;
            return;
        }

        if (root.left != null) {
            dfs(root.left, num);
        }

        if (root.right != null) {
            dfs(root.right, num);
        }

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
