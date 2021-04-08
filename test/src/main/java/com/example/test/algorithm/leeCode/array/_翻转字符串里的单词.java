package com.example.test.algorithm.leeCode.array;
/**
 * @Description 
 * @author leiel
 * @Date 2020/6/10 7:30 PM
 */

public class _翻转字符串里的单词 {

    public static String reverseWords(String s) {

        if(s == null) return "";

        char[] charvalue = s.toCharArray();

        //长度
        int len = 0;

        //消除多余的空格
        int cur = 0 ;

        //前一个是否为空格
        boolean pre = true;
        for (int i = 0; i < charvalue.length; i++) {

            if(charvalue[i] != ' ') {
                charvalue[cur] = charvalue[i];
                cur++;
                pre = false;
            }else if(pre == false){
                charvalue[cur] = charvalue[i];
                cur++;
                pre = true;
            }

        }

        len = pre ? (cur - 1) : cur;

        if(len < 0) {
            return " ";
        }

        reverse(charvalue, 0, len);

        //对每个单词进行逆序
        int preIndex = -1;
        for (int i = 0; i < len; i++) {

            if(charvalue[i] != ' ') {
                continue;
            }
            reverse(charvalue, preIndex + 1, i);
            preIndex = i;

        }
        reverse(charvalue, preIndex + 1, len);

        return new String(charvalue, 0, len);

    }

    /**
     * 翻转数组 给定其实位置
     * @param ch
     * @param l
     * @param r
     */
    private static void reverse(char[] ch, int l, int r) {

        //左指针
        int li = l;
        //右指针
        int ri = r;
        ri--;
        while (li < ri) {

            char temp = ch[li];
            ch[li] = ch[ri];
            ch[ri] = temp;
            li++;
            ri--;

        }
        System.out.println(String.valueOf(ch));

    }

    public static void main(String[] args) {

        String s = " ";
        reverseWords(s);
    }

}
