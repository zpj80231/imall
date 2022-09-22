package com.imall.notice.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhangpengjun
 * @date 2022/09/22
 */
public class FastJsonUtils {

    /**
     * 递归读取所有的key
     *
     * @param jsonObject
     */
    public static StringBuffer getAllKey(JSONObject jsonObject) {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<String> keys = jsonObject.keySet().iterator();// jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            stringBuffer.append(key.toString()).append(",");
            if (jsonObject.get(key) instanceof JSONObject) {
                JSONObject innerObject = (JSONObject) jsonObject.get(key);
                stringBuffer.append(getAllKey(innerObject));
            } else if (jsonObject.get(key) instanceof JSONArray) {
                JSONArray innerObject = (JSONArray) jsonObject.get(key);
                stringBuffer.append(getAllKey(innerObject));
            }
        }
        return stringBuffer;
    }

    private static StringBuffer getAllKey(JSONArray json1) {
        StringBuffer stringBuffer = new StringBuffer();
        if (json1 != null) {
            Iterator i1 = json1.iterator();
            while (i1.hasNext()) {
                Object key = i1.next();
                if (key instanceof JSONObject) {
                    JSONObject innerObject = (JSONObject) key;
                    stringBuffer.append(getAllKey(innerObject));
                } else if (key instanceof JSONArray) {
                    JSONArray innerObject = (JSONArray) key;
                    stringBuffer.append(getAllKey(innerObject));
                } else {
                }
            }
        }
        return stringBuffer;
    }

    private final static String st1 = "{\n" +
            "    \"devices\":[\n" +
            "        {\n" +
            "            \"deviceId\":\"f9333b87fed66641\",\n" +
            "            \"services\":[\n" +
            "                {\n" +
            "                    \"data\":{\n" +
            "                        \"action\":\"det_rslt\",\n" +
            "                        \"detects\":[\n" +
            "                            {\n" +
            "                                \"attributes\":{\n" +
            "                                    \"body\":{\n" +
            "                                        \"exist_smoke\":1\n" +
            "                                    }\n" +
            "                                },\n" +
            "                                \"blur\":-0.000030703289667144418,\n" +
            "                                \"camera_exinfo\":\"\",\n" +
            "                                \"camera_id\":1,\n" +
            "                                \"camera_ip\":\"192.168.0.79\",\n" +
            "                                \"camera_name\":\"Senscape2\",\n" +
            "                                \"confidence\":0.40967351198196411,\n" +
            "                                \"detect\":\"data:image/jpeg;base64,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\",\n" +
            "                                \"enlarge_factor\":1.7999999523162842,\n" +
            "                                \"h\":28,\n" +
            "                                \"is_detect\":1,\n" +
            "                                \"latitude\":0,\n" +
            "                                \"location\":\"rtsp://192.168.0.96:8800/test\",\n" +
            "                                \"long_timestamp\":1631696138379,\n" +
            "                                \"longitude\":0,\n" +
            "                                \"score\":40.967350006103516,\n" +
            "                                \"serial\":\"dc8f0c3cf00dee73\",\n" +
            "                                \"source_image\":\"data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD\",\n" +
            "                                \"sub_type\":0,\n" +
            "                                \"timecost\":\"0ms\",\n" +
            "                                \"type\":5,\n" +
            "                                \"u_id\":209,\n" +
            "                                \"w\":32,\n" +
            "                                \"x\":1296,\n" +
            "                                \"y\":303\n" +
            "                            }\n" +
            "                        ],\n" +
            "                        \"deviceBusiness\":{\n" +
            "                            \"serviceId\":null\n" +
            "                        },\n" +
            "                        \"manufacturerId\":\"78777063\",\n" +
            "                        \"model\":\"device_3\",\n" +
            "                        \"nodeId\":\"Senscape2\"\n" +
            "                    },\n" +
            "                    \"eventTime\":\"20210915T165538Z\",\n" +
            "                    \"serviceId\":\"uploadDatas\"\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    public static void main(String[] args) {
        JSONObject jsonObject1 = JSONObject.parseObject(st1);
        StringBuffer stb = getAllKey(jsonObject1);
        String[] stba = stb.toString().split("\\,");
        List<String> stbl = Arrays.asList(stba);

        System.err.println(stbl);
        System.out.println(stbl.contains("exist_smoke"));

    }
}
