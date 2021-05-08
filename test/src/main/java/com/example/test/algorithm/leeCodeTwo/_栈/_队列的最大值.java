package com.example.test.algorithm.leeCodeTwo._栈;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Description 
 * @author leiel 使用单调队列实现
 * 单调队列，不遵循FIFO
 * @Date 2021/4/9 8:26 AM
 */

public class _队列的最大值 {
    Queue<Integer> q;
    Deque<Integer> d;

    public _队列的最大值() {
        q = new LinkedList<Integer>();
        d = new LinkedList<Integer>();
    }

    public int max_value() {
        if (d.isEmpty()) {
            return -1;
        }
        return d.peekFirst();
    }

    public void push_back(int value) {
        while (!d.isEmpty() && d.peekLast() < value) {
            d.pollLast();
        }
        d.offerLast(value);
        q.offer(value);
    }

    public int pop_front() {
        if (q.isEmpty()) {
            return -1;
        }
        int ans = q.poll();
        if (ans == d.peekFirst()) {
            d.pollFirst();
        }
        return ans;
    }


}
