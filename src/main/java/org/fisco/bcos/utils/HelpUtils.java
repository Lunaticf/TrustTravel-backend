package org.fisco.bcos.utils;

import com.alibaba.fastjson.JSONObject;

public class HelpUtils {
    public static String getResJson(String statusCode, boolean success, String dataName, Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status_code", statusCode);
        jsonObject.put("success", success);
        jsonObject.put(dataName, data);
        return jsonObject.toJSONString();
    }
}
