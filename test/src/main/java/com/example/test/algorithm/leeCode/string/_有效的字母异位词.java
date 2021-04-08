package com.example.test.algorithm.leeCode.string;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/valid-anagram/
 * @Description 
 * @author leiel
 * @Date 2020/6/9 2:14 PM
 */
public class _有效的字母异位词 {

    /**
     * 输入: s = "anagram", t = "nagaram"
     * 输出: true
     * @param s
     * @param t
     * @return
     */
    public static boolean isAnagram(String s, String t) {

        if(s == null || t == null) return false;

        if(s.length() != t.length()) return false;

        //统计每个单词的数量  hash表  map
        Map<Character, Integer> sMap = new HashMap<>();
        Map<Character, Integer> tMap = new HashMap<>();

        char[] schar = s.toCharArray();
        char[] tchar = t.toCharArray();

        for(int i = 0; i < schar.length; i++) {

            if(sMap.containsKey(schar[i])) {
                sMap.put(schar[i], sMap.get(schar[i])+1);
            }else {
                sMap.put(schar[i], 1);
            }

        }

        for(int i = 0; i < tchar.length; i++) {

            if(tMap.containsKey(tchar[i])) {
                tMap.put(tchar[i], tMap.get(tchar[i])+1);
            }else {
                tMap.put(tchar[i], 1);
            }

        }

        //比较两个map的数量
        for(Character ch : sMap.keySet()) {

            if(!sMap.get(ch).equals(tMap.get(ch))) {
                return false;
            }

        }

        return true;

    }

    /**
     * 输入: s = "anagram", t = "nagaram"
     * 输出: true
     * 不适用hash  由于题目给的是   你可以假设字符串只包含小写字母--即最多有26种选择
     * @param s
     * @param t
     * @return
     */
    public static boolean isAnagramTwo(String s, String t) {

        if(s == null || t == null) return false;

        if(s.length() != t.length()) return false;

        //统计每个单词的数量  开辟固定空间
        int[] count = new int[26];

        char[] schar = s.toCharArray();
        char[] tchar = t.toCharArray();

        for(int i = 0; i < s.length(); i++) {

            count[schar[i]-'a']++;

        }

        for(int i = 0; i < t.length(); i++) {

            count[tchar[i]-'a']--;
            if(count[tchar[i]-'a'] < 0) {
                return false;
            }

        }

        return true;

    }

    public static void main(String[] args) {
        String s = "anagram";
        String t = "nagaram";


        System.out.println(isAnagramTwo(s,t));
    }

}
