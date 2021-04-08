package com.example.test.diamond;
/**
 * @Description 
 * @author leiel
 * @Date 2020/4/8 5:11 PM
 */

public class DiamondTest {

    public static void main(String[] args) {

        String s1 = new String("zs");
        String s2 = new String("zs");
        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));
        String s3 = "zs";
        String s4 = "zs";
        System.out.println(s3 == s4);
        System.out.println(s3 == s1);
        String s5 = "zszs";
        String s6 = s3+s4;
        System.out.println(s5 == s6);
        final String s7 = "zs";
        final String s8 = "zs";
        String s9 = s7+s8;
        System.out.println(s5 == s9);
        final String s10 = s3+s4;
        System.out.println(s5 == s10);

    }

}
