package com.quantchi.tianji.service.search.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import sun.misc.BASE64Encoder;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by louies on 2019/7/9.
 */
@Slf4j
public class AlyOcrMingPian {

    public static void main(String[] args) {
            String host = "http://dm-57.data.aliyun.com";
            String path = "/rest/160601/ocr/ocr_business_card.json";
            String method = "POST";
            String appcode = "693a059106f648c9b65f0f01d2bd73d0";
            Map<String, String> headers = new HashMap<String, String>();
            //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
            headers.put("Authorization", "APPCODE " + appcode);
            //根据API的要求，定义相对应的Content-Type
            headers.put("Content-Type", "application/json; charset=UTF-8");
            headers.put("Access-Control-Allow-Origin","*");
            Map<String, String> querys = new HashMap<String, String>();
            String imgUrl = getImageStr("c:\\test\\bb.jpg");
            String bodys = "{\"image\":"+"\""+imgUrl+"\"}";

            try {
                /**
                 * 重要提示如下:
                 * HttpUtils请从
                 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
                 * 下载
                 *
                 * 相应的依赖请参照
                 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
                 */
                HttpResponse response = com.quantchi.tianji.service.search.utils.HttpUtils.doPost(host, path, method, headers, querys, bodys);
                System.out.println(response.toString());
                //获取response的body
                System.out.println(EntityUtils.toString(response.getEntity()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    /**
     * @param
     * @return String
     */
    public static String generateMingPian(String picLocation) {
        String host = "http://dm-57.data.aliyun.com";
        String path = "/rest/160601/ocr/ocr_business_card.json";
        String method = "POST";
        String appcode = "693a059106f648c9b65f0f01d2bd73d0";
        String jsonStr="";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");
        headers.put("Access-Control-Allow-Origin", "*");
        Map<String, String> querys = new HashMap<String, String>();
        String imgBase64 = getImageStr(picLocation);
        String bodys = "{\"image\":" + "\"" + imgBase64 + "\"}";
        try {
            HttpResponse response = com.quantchi.tianji.service.search.utils.HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response);
            log.info("调用阿里名片接口返回数据:{}",response);
            //获取response的body
            jsonStr=EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            log.error("调用阿里名片扫描接口异常:{}", e.getMessage());
            System.out.println("调用阿里名片扫描接口异常:"+ e.getMessage());
        }
        return jsonStr;
    }

     /**
     * @param imgFile
     * @return String
     */
    public static String getImageStr(String imgFile)
    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try
        {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e) {
          System.out.println(e.getMessage());
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }


}
