package com.example.test.algorithm.leeCode.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/same-tree/
 * @Description 
 * @author leiel
 * @Date 2020/8/7 9:17 AM
 */

public class _相同的树 {

    /**
     * 输入:       1         1
     *           / \       / \
     *          2   3     2   3
     *
     *         [1,2,3],   [1,2,3]
     *
     * 输出: true
     *
     * 遍历两棵树
     *
     * @param p
     * @param q
     * @return
     */
    public static boolean isSameTree(TreeNode p, TreeNode q) {

        if(p == null && q == null){
            return true;
        }else if(p == null && q != null) {
            return false;
        }else if(q == null && p != null) {
            return false;
        }

        Queue<TreeNode> queuep = new LinkedList<>();
        queuep.add(p);

        Queue<TreeNode> queueq = new LinkedList<>();
        queueq.add(q);

        while(!queuep.isEmpty()) {

            if(queueq.isEmpty()) {
                return false;
            }

            TreeNode nodep = ((LinkedList<TreeNode>) queuep).pop();

            TreeNode nodeq = ((LinkedList<TreeNode>) queueq).pop();

            if(nodep.val != nodeq.val) {
                return false;
            }

            if((nodep.left != null && nodeq.left == null) || (nodeq.left != null && nodep.left == null)) {
                return false;
            }
            if((nodep.right != null && nodeq.right == null) || (nodeq.right != null && nodep.right == null)) {
                return false;
            }

            if(nodep.left != null && nodeq.left != null) {
                queuep.add(nodep.left);
                queueq.add(nodeq.left);
                if(nodep.left.val != nodeq.left.val) {
                    return false;
                }
            }

            if(nodep.right != null && nodeq.right != null) {
                queuep.add(nodep.right);
                queueq.add(nodeq.right);
                if(nodep.right.val != nodeq.right.val) {
                    return false;
                }
            }


        }

        return true;

    }

    /**
     * [1,null,2,3]
     * [1,null,2,null,3]
     * @param args
     */
    public static void main(String[] args) {

        TreeNode nodep = new TreeNode(1);
        nodep.right = new TreeNode(1);

        TreeNode nodeq = new TreeNode(1);
        nodeq.right = new TreeNode(1);

        System.out.println(isSameTree(nodep, nodeq));

    }

}
