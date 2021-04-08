package com.example.test.algorithm.leeCode.array;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description 
 * @author leiel
 * @Date 2020/7/9 1:39 PM
 */

public class _恢复空格 {

    /**
     * 输入：
     * dictionary = ["looked","just","like","her","brother"]
     * sentence = "jesslookedjustliketimherbrother"
     * 输出： 7
     * 解释： 断句后为"jess looked just like tim her brother"，共7个未识别字符。
     *
     * @param dictionary
     * @param sentence
     * @return
     */
    public int respace(String[] dictionary, String sentence) {

        Set<String> dic = new HashSet<>();
        for(String str: dictionary) dic.add(str);

        int n = sentence.length();
        //dp[i]表示sentence前i个字符所得结果
        int[] dp = new int[n+1];
        for(int i=1; i<=n; i++){
            //先假设当前字符作为单词不在字典中
            dp[i] = dp[i-1]+1;
            for(int j=0; j<i; j++){
                if(dic.contains(sentence.substring(j,i))){
                    dp[i] = Math.min(dp[i], dp[j]);
                }
            }
        }
        return dp[n];

    }

}
