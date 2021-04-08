package com.example.test.algorithm.leeCode.string;
/**
 * https://leetcode-cn.com/problems/add-strings/
 * @Description 
 * @author leiel
 * @Date 2020/8/3 9:09 AM
 */

public class _字符串相加 {

    /**
     * 给定两个字符串形式的非负整数 num1 和num2 ，计算它们的和。
     *
     * 注意：
     *
     * num1 和num2 的长度都小于 5100.
     * num1 和num2 都只包含数字 0-9.
     * num1 和num2 都不包含任何前导零。
     * 你不能使用任何內建 BigInteger 库， 也不能直接将输入的字符串转换为整数形式。
     * @param num1
     * @param num2
     * @return  双指针减少时间复杂度
     */
    public static String addStrings(String num1, String num2) {

        StringBuilder res = new StringBuilder("");
        int i = num1.length() - 1, j = num2.length() - 1, carry = 0;
        while(i >= 0 || j >= 0){
            int n1 = i >= 0 ? num1.charAt(i) - '0' : 0;
            int n2 = j >= 0 ? num2.charAt(j) - '0' : 0;
            int tmp = n1 + n2 + carry;
            carry = tmp / 10;
            res.append(tmp % 10);
            i--; j--;
        }
        if(carry == 1) res.append(1);
        return res.reverse().toString();
    }

    public static void main(String[] args) {

         String num1 = "78453";

         String num2 = "27659";

        System.out.println(addStrings(num1, num2));

    }

}
