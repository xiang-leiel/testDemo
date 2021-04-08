package com.example.test.utils;

import org.apache.logging.log4j.util.PropertiesUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/26 2:19 PM
 */

public class PropertiesUtils {

    private static final String DEFAULT_PROPERTIES= "/application.properties";

    /**
     * 获取properties属性值
     * @param propKey
     * @return
     */
    public static String getPropValue(String propKey){
        try {
            Properties props = new Properties();
            InputStream inputStream = PropertiesUtil.class.getResourceAsStream(DEFAULT_PROPERTIES);
            //*.properties配置文件，要使用UTF-8编码，否则会现中文乱码问题
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            props.load(bf);
            return props.getProperty(propKey);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringTest(String value) {
        return value;
    }
}
