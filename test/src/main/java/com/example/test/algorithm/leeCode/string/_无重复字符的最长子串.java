package com.example.test.algorithm.leeCode.string;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
 * @Description 
 * @author leiel
 * @Date 2020/6/11 10:02 AM
 */
public class _无重复字符的最长子串 {

    /**
     * 输入: "abcabcbb"
     * 输出: 3
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {

        if(s == null) return 0;

        int maxLen = 0;

        Map<Character, Object> map = new HashMap<>();

        char[] ch = s.toCharArray();

        int curLen = 1;
        for(int i = ch.length-1; i >= 0; i--) {

            map.put(ch[i], 1);
            int curIndex = i-1;
            while(curIndex >= 0 && !map.containsKey(ch[curIndex])) {
                curLen++;
                map.put(ch[curIndex], 1);
                curIndex--;
            }
            maxLen = Math.max(maxLen, curLen);
            curLen = 1;
            map.clear();

        }

        return maxLen;
    }


    public static int lengthOfLongestSubstringTwo(String s) {

        if(s == null) return 0;

        if(s.length() == 0) return 0;

        int maxLen = 1;

        Map<Character, Integer> map = new HashMap<>();

        char[] ch = s.toCharArray();

        //以i-1位置字符结尾的最长不重复字符串的开始索引
        int li = 0;
        map.put(ch[0], 0);
        for(int i = 1; i < ch.length; i++) {

            //i位置字符上次出现的索引
            int pi = map.getOrDefault(ch[i], -1);

            if(li <= pi) {
                li = pi+1;
            }
            map.put(ch[i], i);

            maxLen = Math.max(maxLen, i - li + 1);

        }

        return maxLen;
    }

    public static void main(String[] args) {

        String s = "abcabcbb";

        System.out.println(lengthOfLongestSubstringTwo(s));

    }

}
