package com.example.test.algorithm.leeCode.string;
/**
 * https://leetcode-cn.com/problems/string-rotation-lcci/
 * @Description 
 * @author leiel
 * @Date 2020/6/8 10:52 AM
 */
public class _字符串轮转 {

    /**
     * 将s1字符串拼接,从头扫描,是否能获取到相应s2
     */
    public static boolean isFlipedString(String s1, String s2) {

        if((s2 == null && s1 != null) || (s2 != null && s1 == null)) {
            return false;
        }

        if(s1.length() != s2.length()) {
            return false;
        }
        String splice = s1 + s1;
        return splice.contains(s2);

    }

    public static void main(String[] args) {
        String s1 = "ab";
        String s2 = "ab";
        Boolean boo = isFlipedString(s1, s2);
        System.out.println(boo);
    }
}
