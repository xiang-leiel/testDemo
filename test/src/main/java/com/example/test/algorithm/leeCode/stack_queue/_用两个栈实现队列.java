package com.example.test.algorithm.leeCode.stack_queue;

import java.util.LinkedList;
import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/yong-liang-ge-zhan-shi-xian-dui-lie-lcof/
 * @Description 
 * @author leiel
 * @Date 2020/6/30 1:38 PM
 */
public class _用两个栈实现队列 {

    private Stack<Integer> stack1;

    private Stack<Integer> stack2;

    /**
     * 输入：
     * ["CQueue","appendTail","deleteHead","deleteHead"]
     * [[],[3],[],[]]
     * 输出：[null,null,3,-1]
     *
     * 栈先入后出  队列是先入先出
     */
    public _用两个栈实现队列() {

        stack1 = new Stack<>();

        stack2 = new Stack<>();

    }

    public void appendTail(int value) {

        stack1.push(value);

    }

    public int deleteHead() {

        // 如果第二个栈为空
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        if (stack2.isEmpty()) {
            return -1;
        } else {
            int deleteItem = stack2.pop();
            return deleteItem;
        }

    }

}
