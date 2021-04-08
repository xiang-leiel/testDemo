package com.example.test.basis.hashmap;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/22 5:28 PM
 */

public class HashMapDemo {

    public static void main(String[] args) {

        HashMap<String, Object> map = new HashMap<>();

        map.put("name", 1);
        map.put(null,null);

        map.remove("name");

        Hashtable<String, Object> table = new Hashtable<>();
        table.put(null,"hahah ");

        Map<String, Object> mapLinked = new LinkedHashMap();

    }

}
