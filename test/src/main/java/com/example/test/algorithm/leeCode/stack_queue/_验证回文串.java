package com.example.test.algorithm.leeCode.stack_queue;

import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/valid-palindrome/
 * @Description 
 * @author leiel
 * @Date 2020/6/19 11:07 AM
 */

public class _验证回文串 {

    /**
     * 输入: "A man, a plan, a canal: Panama"
     * 输出: true
     *
     * 输入: "race a car"
     * 输出: false
     * 双指针比较合适哈哈哈
     * @param s
     * @return
     */
    public static boolean isPalindrome(String s) {

        s = s.toLowerCase();

        char[] ch = s.toCharArray();

        int lIndex = 0;

        int rIndex = ch.length - 1;

        while(lIndex < rIndex) {


            //判断是否为字母 返回true则为字母，入栈
            if(isLetter(s.charAt(lIndex)) && isLetter(s.charAt(rIndex))) {

                String a = String.valueOf(s.charAt(lIndex));

                String b = String.valueOf(s.charAt(rIndex));

                if(!a.equalsIgnoreCase(b)) {
                    return false;
                }
                lIndex++;
                rIndex--;

            }else if(isLetter(s.charAt(lIndex)) && !isLetter(s.charAt(rIndex))) {
                rIndex--;
            }else if(!isLetter(s.charAt(lIndex)) && isLetter(s.charAt(rIndex))) {
                lIndex++;
            }else{
                lIndex++;
                rIndex--;
            }

        }

        return true;

    }

    public static boolean isLetter(char ch) {

        if(Character.isLetterOrDigit(ch)) {
            return true;
        }
        return false;

    }

    public static void main(String[] args) {

/*        String  a="Aa";
        String  b="aA";
        System.out.println(a.equalsIgnoreCase(b));

        String s = "p";

        System.out.println(Character.isLowerCase(s.charAt(0)) || Character.isUpperCase(s.charAt(0)));*/

        String str = "0P";

        System.out.println(isPalindrome(str));


    }

}
