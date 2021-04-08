package com.example.test.algorithm.leeCode.tree;
/**
 * https://leetcode-cn.com/problems/path-sum/
 * @Description 
 * @author leiel
 * @Date 2020/7/7 5:17 PM
 */

public class _路径总和 {

    /**
     *              5
     *              / \
     *             4   8
     *            /   / \
     *           11  13  4
     *          /  \      \
     *         7    2      1
     *
     * @param root
     * @param sum
     * @return
     */
    public boolean hasPathSum(TreeNode root, int sum) {

        if (root == null) {
            return false;
        }

        if (root.left == null && root.right == null) {
            return sum == root.val;
        }

        return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);

    }

    //另一种方式是用队列实现  两个队列，层序遍历 将遍历的节点子节点存放在第一个队列，将值存放到第二个队列

}
