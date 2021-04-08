package com.example.test.algorithm.leeCode.tree;
/**
 * https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/
 * @Description 
 * @author leiel
 * @Date 2020/7/28 2:25 PM
 */

public class _二叉树的最大深度 {

    /**
     * 示例：
     * 给定二叉树 [3,9,20,null,null,15,7]，层序
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {

        if(root == null) {
            return 0;
        }
        int l = maxDepth(root.left)+1;

        int r = maxDepth(root.right)+1;

        return Math.max(l, r);
    }


}
