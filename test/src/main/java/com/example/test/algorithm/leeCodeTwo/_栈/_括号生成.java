package com.example.test.algorithm.leeCodeTwo._栈;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2021/4/25 8:36 AM
 */

public class _括号生成 {

    /**
     *
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {

        List<String> strList = new ArrayList<>();

        generateAll(new char[2 * n], 0, strList);
        return strList;

    }

    public void generateAll(char[] current, int pos, List<String> result) {
        if (pos == current.length) {

            if(check(current)) {
                result.add(new String(current));
            }

        } else {
            current[pos] = '(';
            generateAll(current, pos + 1, result);
            current[pos] = ')';
            generateAll(current, pos + 1, result);
        }
    }

    /**
     *
     * @param
     */
    private boolean check(char[] current) {
        int balance = 0;

        for (char c : current) {

            if (c == '(') {
                ++balance;
            } else {
                --balance;
            }
            if (balance < 0) {
                return false;
            }

        }

        if (balance == 0) {
            return true;
        }


        return false;
    }

    public static void main(String[] args) {
        //generateParenthesis(2);
    }

}
