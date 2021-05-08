package com.example.test.algorithm.leeCodeTwo._栈;
/**
 * @Description 
 * @author leiel
 * @Date 2021/5/8 8:11 AM
 */

public class _整数反转 {

    public static int reverse(int x) {

        int rev = 0;
        while (x != 0) {
            if (rev < Integer.MIN_VALUE / 10 || rev > Integer.MAX_VALUE / 10) {
                return 0;
            }
            int digit = x % 10;
            x /= 10;
            rev = rev * 10 + digit;
        }
        return rev;

    }

    public static void main(String[] args) {
        reverse(-1230);
    }

}
