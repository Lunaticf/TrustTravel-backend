package org.fisco.bcos.utils;

import com.alibaba.fastjson.JSONObject;
import org.fisco.bcos.model.CommonResult;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;

import java.util.HashMap;

public class HelpUtils {
    public static String getResJson(String statusCode, boolean success, String dataName, Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status_code", statusCode);
        jsonObject.put("success", success);
        jsonObject.put(dataName, data);
        return jsonObject.toJSONString();
    }

    public static void addTxDetails(HashMap hashMap, CommonResult commonResult, TransactionReceipt receipt) {
        hashMap.put("txHash", receipt.getTransactionHash());
        commonResult.setData(hashMap);
    }

}
