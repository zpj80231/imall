package service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author zhangpengjun
 * @date 2022/10/10
 */
public interface EventSendingService {

    void httpSending(JSONObject event, Object pushInfo);

}
