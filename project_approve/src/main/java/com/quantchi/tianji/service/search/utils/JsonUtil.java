package com.quantchi.tianji.service.search.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 *
 */
public final class JsonUtil {
    private JsonUtil() {

    }
    /**
     * 获取json文件数据
     * @param model jsom文件里的模块
     * @param jsonFile json文件地址 如：static/imp_usa.json
     * @return 返回List<Object>数据
     * @throws IOException io
     */
    public static List<Object> getJsonModelDataToList(String model, String jsonFile) throws IOException {

        InputStream in = JsonUtil.class.getClassLoader().getResourceAsStream(jsonFile);
        byte[] bytes = new byte[0];
        bytes = new byte[in.available()];
        in.read(bytes);
        String  input = new String(bytes);
        JSONArray btnArray = null;
        JSONObject jsonObject = JSONObject.fromObject(input);
        List<Object> result = new LinkedList<>();
        if (jsonObject != null) {
            if ("index".equals(model) || "dataflag".equals(model)) {
                result.add(jsonObject.get(model));
                return result;
            } else {
                btnArray = jsonObject.getJSONArray(model);
            }
        }
        Iterator<Object> num = btnArray.iterator();
        while (num.hasNext()) {
            JSONObject btn = (JSONObject) num.next();
            Map<String, Object> map1 = new LinkedHashMap();
            for (Iterator<?> iter = btn.keys(); iter.hasNext(); ) {
                String key = (String) iter.next();
                Object value = btn.get(key);
                map1.put(key, value);
            }
            result.add(map1);
        }
        return result;
    }

    public static JSONObject getJsonDataToList(String schema) {
        /*
        ClassPathResource resource = new ClassPathResource(schema);
        File filePath = resource.getFile();
        String input = FileUtils.readFileToString(filePath, "UTF-8");
        */
        InputStream in = JsonUtil.class.getClassLoader().getResourceAsStream(schema);
        byte[] bytes = new byte[0];
        try {
            bytes = new byte[in.available()];
            in.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String  input = new String(bytes);
        JSONObject jsonObject = JSONObject.fromObject(input);
        return jsonObject;
    }
}
