package com.quantchi.tianji.service.search.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2020/2/28 3:02 PM
 */
@Slf4j
public class UUIDUtils {

    public static synchronized List<String> genUuid(String  projectId, int  num){
        List<String>  uuids  =  new ArrayList<>();
        String  lastTime  =  "";
        try  {
            for  (int  i  =  0;  i  <  num;  i++)  {
                //  16位  us
                Long  cutime  =  System.currentTimeMillis()  *  1000;
                //  ns
                Long  nanoTime  =  System.nanoTime();
                //16位us
                cutime  +=  (nanoTime  -  nanoTime  /  1000000  *  1000000)  /  1000;
                String  nowTime  =  String.valueOf(cutime);
                if  (nowTime.equals(lastTime))  {
                    Thread.sleep(0,1);
                    cutime  =  System.currentTimeMillis()  *  1000;
                    nanoTime  =  System.nanoTime();
                    cutime  +=  (nanoTime  -  nanoTime  /  1000000  *  1000000)  /  1000;
                    nowTime  =  String.valueOf(cutime);
                }
                lastTime  =  nowTime;
                String  uuid  =  nowTime.concat(projectId);
                uuids.add(uuid);
            }
        }catch  (Exception  e){
            log.error(String.format("UUID生成冲突，projectId  -  [%s]",  projectId));
        }
        return  uuids;
    }

    public static String getZhaoshangUUId() {
        return genUuid("1020",1).get(0);
    }

    public static void main(String[] args) {
        String str = "dsada|";
        System.out.println(str.substring(0,str.length()-1));
    }
}
