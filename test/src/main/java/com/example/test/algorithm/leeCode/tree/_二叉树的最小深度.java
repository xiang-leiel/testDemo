package com.example.test.algorithm.leeCode.tree;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/minimum-depth-of-binary-tree/
 * @Description 
 * @author leiel
 * @Date 2020/8/21 1:52 PM
 */

public class _二叉树的最小深度 {

    /**
     * 给定二叉树 [3,9,20,null,null,15,7],
     *
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * 返回它的最小深度  2.
     *
     * @param root
     * @return
     */
    public int minDepth(TreeNode root) {

        if(root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }

        int min_depth = Integer.MAX_VALUE;
        if (root.left != null) {
            min_depth = Math.min(minDepth(root.left), min_depth);
        }
        if (root.right != null) {
            min_depth = Math.min(minDepth(root.right), min_depth);
        }

        return min_depth + 1;


    }

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("");

        Map<String, Object> map = new TreeMap<>();
        map.put("","");

        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return 0;
            }
        });

    }

}
