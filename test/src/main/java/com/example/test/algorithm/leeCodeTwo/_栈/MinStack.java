package com.example.test.algorithm.leeCodeTwo._栈;

import java.util.*;

/**
 * @Description 将min()函数复杂度降为O(1)，可通过辅助栈实现
 * @author leiel
 * @Date 2021/4/7 8:27 AM
 */

class MinStack {

    Stack<Integer> A, B;

    /** initialize your data structure here. */
    public MinStack() {
        //存放所有的元素
        A = new Stack<>();
        //栈顶存放栈A的最小元素
        B = new Stack<>();
    }

    public void push(int x) {

        A.add(x);
        if (B.empty() || B.peek() >= x) {
            B.add(x);
        }

    }

    public void pop() {
        if(A.pop().equals(B.peek()))
            B.pop();
    }

    public int top() {
        return A.peek();
    }

    public int min() {
        return B.peek();
    }
}

