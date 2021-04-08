package com.example.test.algorithm.leeCode.tree;

import com.example.test.entity.UserInfo;
import org.springframework.beans.factory.ObjectFactory;

import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/recover-a-tree-from-preorder-traversal/
 * @Description 
 * @author leiel
 * @Date 2020/6/18 4:05 PM
 */

public class _从先序遍历还原二叉树 {

    /**
     * 输入："1-2--3--4-5--6--7"
     *         1  2  2 1  2  2
     * 输出：[1,2,5,3,4,6,7]
     * @param S
     * @return
     */
    public static TreeNode recoverFromPreorder(String S) {

        //最终的返回的根节点
        TreeNode root = null;
        Stack<TreeNode> stack = new Stack<>();

        //记录上一次遍历到第几层
        int prevLevel = 0;

        //当前层
        int cutLevel = 0;

        //节点值
        int val = 0;
        int i = 0;
        while (i < S.length()){

            /**
             * 如果root==null，先设置根节点
             */
            if(root == null){
                //因为节点的值介于 1 和 10 ^ 9 之间，所以需要遍历计算val
                while (i < S.length() && Character.isDigit(S.charAt(i))){
                    val = val * 10 + (S.charAt(i) -  '0');
                    i ++;
                }
                root = new TreeNode(val);

                //根节点入栈
                stack.push(root);
            }else if(S.charAt(i) == '-'){
                cutLevel ++;
                i++;
            }else {
                val = 0;
                while (i < S.length() && Character.isDigit(S.charAt(i))){
                    val = val * 10 + (S.charAt(i) -  '0');
                    i ++;
                }
                /**
                 * 左子树
                 * 如果当前cutLevel > prevLevel,说明是下一级，取栈顶节点
                 * */
                if(cutLevel > prevLevel){
                    TreeNode node = stack.pop();
                    node.left = new TreeNode(val);
                    //入栈的时候，需要将之前栈顶元素先入栈，然后node.left或者node.right再入栈
                    stack.push(node);
                    stack.push(node.left);
                    //然后cutLevel赋给prevLevel，cutLevel置0
                    prevLevel = cutLevel;
                    cutLevel = 0;
                } else {
                    /**
                     * 右子树
                     * 根据cutLevel的值，回溯到上一级
                     */
                    while (stack.size() > cutLevel){
                        stack.pop();
                    }
                    TreeNode node = stack.pop();
                    node.right = new TreeNode(val);
                    stack.push(node);
                    stack.push(node.right);
                    prevLevel = cutLevel;
                    cutLevel = 0;
                }
            }
        }
        return root;

    }

    public static void main(String[] args) {

        String str = "1-401--349---90--88";

        System.out.println(recoverFromPreorder(str));

    }

}
