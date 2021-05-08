package com.example.test.algorithm.leeCodeTwo._栈;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @Description 
 * @author leiel
 * @Date 2021/4/14 8:44 AM
 */

public class _有效的括号 {

    public boolean isValid(String s) {

        char[] chars = s.toCharArray();

        Stack<Character> stack = new Stack<>();

        Map<Character, Character> map = new HashMap<>();

        map.put('(',')');
        map.put('{','}');
        map.put('[',']');

        //利用出栈入栈的顺序
        for (char value : chars) {

            if (stack.isEmpty()) {
                stack.push(value);
                continue;
            }

            if (stack.peek() == value) {
                stack.pop();
            }

            stack.push(value);
        }

        if (stack.isEmpty()) {
            return true;
        }

        return false;

    }

}
