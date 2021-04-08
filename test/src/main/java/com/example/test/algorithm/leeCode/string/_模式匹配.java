package com.example.test.algorithm.leeCode.string;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * https://leetcode-cn.com/problems/pattern-matching-lcci/
 * @Description 
 * @author leiel
 * @Date 2020/6/22 8:36 AM
 */

public class _模式匹配 {

    /**
     * 输入： pattern = "abba", value = "dogcatcatdog"
     * 输出： true
     * @param pattern
     * @param value
     * @return
     */
    public boolean patternMatching(String pattern, String value) {

        //计算出pattern中a 和b的个数
        int count_a = 0, count_b = 0;
        for (char ch: pattern.toCharArray()) {
            if (ch == 'a') {
                ++count_a;
            } else {
                ++count_b;
            }
        }
        if (count_a < count_b) {
            int temp = count_a;
            count_a = count_b;
            count_b = temp;
            char[] array = pattern.toCharArray();
            for (int i = 0; i < array.length; i++) {
                array[i] = array[i] == 'a' ? 'b' : 'a';
            }
            pattern = new String(array);
        }

        if (value.length() == 0) {
            return count_b == 0;
        }

        //模板长度为0 返回false
        if (pattern.length() == 0) {
            return false;
        }

        //枚举pattern中a和b的长度
        for (int len_a = 0; count_a * len_a <= value.length(); ++len_a) {
            int rest = value.length() - count_a * len_a;
            if ((count_b == 0 && rest == 0) || (count_b != 0 && rest % count_b == 0)) {
                int len_b = (count_b == 0 ? 0 : rest / count_b);
                int pos = 0;
                boolean correct = true;
                String value_a = "", value_b = "";
                for (char ch: pattern.toCharArray()) {
                    if (ch == 'a') {
                        String sub = value.substring(pos, pos + len_a);
                        if (value_a.length() == 0) {
                            value_a = sub;
                        } else if (!value_a.equals(sub)) {
                            correct = false;
                            break;
                        }
                        pos += len_a;
                    } else {
                        String sub = value.substring(pos, pos + len_b);
                        if (value_b.length() == 0) {
                            value_b = sub;
                        } else if (!value_b.equals(sub)) {
                            correct = false;
                            break;
                        }
                        pos += len_b;
                    }
                }
                if (correct && !value_a.equals(value_b)) {
                    return true;
                }
            }
        }
        return false;

    }

    public static void main(String[] args) {
        String str = "abcd";

        AtomicInteger i = new AtomicInteger(1);

        i.compareAndSet(1, 5);

        System.out.println(i.get());

        //左闭右开
        System.out.println(str.substring(0, 3));
    }

}
