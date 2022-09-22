package com.imall.notice.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 解析从mq过来的消息，获取指定字段信息
 *
 * @author zhangpengjun
 * @date 2022/6/17
 */
public class AsteriskMQGetMsgUtils {

    public static String getValueFromKey(String msg, String key) {
        String result = null;
        JSONObject jsonObject = JSON.parseObject(msg);
        if (jsonObject.containsKey(key)) {
            result = jsonObject.getString(key);
        }
        return result;
    }

    public static String callerIdNum(String msg) {
        return getValueFromKey(msg, "callerIdNum");
    }

    public static String channel(String msg) {
        return getValueFromKey(msg, "channel");
    }

    public static String linkedId(String msg) {
        return getValueFromKey(msg, "linkedId");
    }

    public static String connectedLineNum(String msg) {
        return getValueFromKey(msg, "connectedLineNum");
    }
}
