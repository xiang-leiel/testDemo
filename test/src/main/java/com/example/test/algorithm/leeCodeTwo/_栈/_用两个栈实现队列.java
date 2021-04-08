package com.example.test.algorithm.leeCodeTwo._栈;

import java.util.Stack;

/**
 * @Description 
 * @author leiel
 * @Date 2021/4/8 8:21 AM
 */

public class _用两个栈实现队列 {

    Stack<Integer> A, B;

    public _用两个栈实现队列() {

        //初始栈
        A = new Stack<>();
        //队列栈
        B = new Stack<>();

    }

    public void appendTail(int value) {

        if (A.isEmpty()) {
            A.push(value);
        }

    }

    public int deleteHead() {

        if (B.isEmpty()) {

            if (A.isEmpty()) {
                return -1;
            } else {
                while (!A.isEmpty()) {
                    B.push(A.pop());
                }
            }

        }


        return B.pop();
    }

}
