package com.example.test.algorithm.leeCodeTwo._栈;

import java.util.Stack;

/**
 * @Description 
 * @author leiel
 * @Date 2021/4/12 8:39 AM
 */

public class _验证栈序列 {

    public boolean validateStackSequences(int[] pushed, int[] popped) {

        int N = pushed.length;
        Stack<Integer> stack = new Stack();

        int j = 0;
        for (int x: pushed) {
            stack.push(x);
            while (!stack.isEmpty() && j < N && stack.peek() == popped[j]) {
                stack.pop();
                j++;
            }
        }

        return j == N;

    }

}
