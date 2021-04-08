package com.example.test.algorithm.leeCode.tree;
/**
 * https://leetcode-cn.com/problems/balanced-binary-tree/
 * @Description 
 * @author leiel
 * @Date 2020/8/17 1:44 PM
 */
public class _平衡二叉树 {

    /**
     * 示例 1:
     *
     * 给定二叉树 [3,9,20,null,null,15,7]
     *
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * 返回 true 。
     *
     * 平衡二叉树的概念  左右子树相差不超过1
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {

        return height(root) >= 0;
    }

    public int height(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = height(root.left);
        int rightHeight = height(root.right);
        if (leftHeight == -1 || rightHeight == -1 || Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        } else {
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }

}
