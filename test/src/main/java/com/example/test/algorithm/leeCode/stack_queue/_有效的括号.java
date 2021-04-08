package com.example.test.algorithm.leeCode.stack_queue;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/valid-parentheses/
 * @Description 
 * @author leiel
 * @Date 2020/5/25 9:28 AM
 */

public class _有效的括号 {

    private static Stack<Character> stack = new Stack<>();

    /**
     * 输入: "()[]{}"
     * 输出: true
     * @param s
     * @return
     */
    public static boolean isValid(String s) {

        if(s == null ) return true;

        if(s.length() == 1) return false;

        char[] chars = s.toCharArray();

        Map<Character, Character> map = new HashMap<>();
        map.put('(',')');
        map.put('[',']');
        map.put('{','}');

        for(char value : chars) {

            //如果是左括号就入栈
            if(map.keySet().contains(value)) {
                stack.push(value);
            }else {
                if(stack.empty()) {
                    return false;
                }
                char key = stack.peek();
                if (map.get(key).equals(value)) {
                    stack.pop();
                }else {
                    return false;
                }
            }

        }

        if(!stack.empty()) {
            return false;
        }

        return true;

    }

    public static void main(String[] args) {
        String test = "[])";

        System.out.println(isValid(test));
    }

}
