package com.quantchi.tianji.service.search.utils;

import net.sf.json.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * @Description 经纬度获取地址
 * @author leiel
 * @Date 2020/2/3 10:07 AM
 */
@Data
@Slf4j
public class AddressUntils {

    /**
     *根据经纬度获取省市区
     * @param longitude
     * @param lat
     * @return
     */
    public static String getAdd(String longitude, String lat){
        //ak为百度api获取
        String urlString = "http://api.map.baidu.com/reverse_geocoding/v3/?ak=CZOzs5ZGW8s4Rj0D0oohjdPXLdW9ErOB&output=json&coordtype=wgs84ll&location="+lat+","+longitude;
        String res = "";
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                res += line+"\n";
            }
            in.close();

            JSONObject jsonObject = JSONObject.fromObject(res);
            JSONObject j_2 = JSONObject.fromObject(jsonObject.getString("result"));
            JSONObject j_3 = JSONObject.fromObject(j_2.getString("addressComponent"));
            res = j_3.getString("province");
        } catch (Exception e) {
            log.error("经纬度转换地区出错" + e.getMessage());
        }
        return res;

    }

    public static void main(String[] args) {
       // getAdd("121.111213","20.253231");
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(4);
        list1.add(5);
        List<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(2);
        list2.add(3);

        list1.removeAll(list2);

        System.out.println(list1);


        String value = "你好啊dsdadasdasd";

        System.out.println(value.length());

    }
}
