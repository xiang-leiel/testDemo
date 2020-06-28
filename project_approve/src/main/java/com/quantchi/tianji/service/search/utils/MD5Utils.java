package com.quantchi.tianji.service.search.utils;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description 
 * @author leiel
 * @Date 2020/3/19 2:50 PM
 */

public class MD5Utils {


    /**利用MD5进行加密*/
    public static String encode(String str){
        //确定计算方法
        String newstr = null;
        try{
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return newstr;
    }

    public static void main(String[] args) {
        System.out.println(encode("123456"));
    }

}
