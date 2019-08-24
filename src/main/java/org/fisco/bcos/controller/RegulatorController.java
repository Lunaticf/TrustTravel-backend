package org.fisco.bcos.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.fisco.bcos.eventModel.HotelEvent;
import org.fisco.bcos.model.TrustTravel;
import org.fisco.bcos.model.UserExp;
import org.fisco.bcos.utils.HelpUtils;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.Request;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosTransaction;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosTransactionReceipt;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tuples.generated.Tuple3;
import org.fisco.bcos.web3j.tx.txdecode.BaseException;
import org.fisco.bcos.web3j.tx.txdecode.EventResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 * 监管节点 控制层
 */
@RestController
public class RegulatorController {

    @Autowired
    private UserExp userExp;

    @Autowired Web3j web3j;

    // 查询积分
    @GetMapping("/exp/{username}")
    public String exp(@PathVariable("username") String username)  {
        HashMap map = new HashMap();
        try {
            Tuple2<BigInteger, BigInteger> tuple2 = userExp.select(username).send();

            map.put("status", tuple2.getValue1());
            map.put("exp", tuple2.getValue2());
            return JSON.toJSONString(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(map);
    }

    // 设置积分
    @PostMapping("/exp/set")
    public void setExp(@RequestParam("username") String username, @RequestParam("exp") int exp) {
        try {
            userExp.set_user_exp(username, BigInteger.valueOf(exp)).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 累加积分
    @PostMapping("/exp/update")
    public void updateExp(@RequestParam("username") String username, @RequestParam("exp") int exp) {
        try {
            userExp.update_exp(username, BigInteger.valueOf(exp)).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 解析交易event
    @GetMapping(value="/legal/tx/{hash}", produces = "application/json; charset=utf-8")
    public String decoderHotelTx(@PathVariable("hash") String hash) {
        HashMap res = new HashMap();

        try {
            BcosTransactionReceipt receipt = web3j.getTransactionReceipt(hash).send();
            Optional<TransactionReceipt> transactionReceipt = receipt.getTransactionReceipt();
            List<Log> logs =
                    transactionReceipt.get().getLogs();
            Map<String, List<List<EventResultEntity>>> eventMap = HelpUtils.getTxDecoder4Travel().decodeEventReturnObject(logs);

            Set set = eventMap.keySet();

            String eventType = (String)set.iterator().next();

            res.put("eventType", eventType.substring(0, eventType.indexOf('(')));
            res.put("eventData", eventMap.get(eventType).get(0));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (BaseException e) {
            e.printStackTrace();
        }

        return JSON.toJSONString(res);
    }

    // 价格举证
    @GetMapping(value="/legal/price", produces = "application/json; charset=utf-8")
    public String preventFamiliar(@RequestParam("hash1") String hash1, @RequestParam("hash2") String hash2) {
        HashMap res = new HashMap();

        try {
            BcosTransactionReceipt receipt = web3j.getTransactionReceipt(hash1).send();
            Optional<TransactionReceipt> transactionReceipt = receipt.getTransactionReceipt();
            List<Log> logs =
                    transactionReceipt.get().getLogs();
            Map<String, List<List<EventResultEntity>>> eventMap1 = HelpUtils.getTxDecoder4Travel().decodeEventReturnObject(logs);
            Set set1 = eventMap1.keySet();

            receipt = web3j.getTransactionReceipt(hash2).send();
            transactionReceipt = receipt.getTransactionReceipt();
            logs =
                    transactionReceipt.get().getLogs();
            Map<String, List<List<EventResultEntity>>> eventMap2 = HelpUtils.getTxDecoder4Travel().decodeEventReturnObject(logs);
            Set set2 = eventMap2.keySet();

            String eventType1 = (String)set1.iterator().next();
            String eventType2 = (String)set2.iterator().next();

            List<EventResultEntity> list1 = eventMap1.get(eventType1).get(0);
            List<EventResultEntity> list2 = eventMap2.get(eventType2).get(0);

            boolean priceProblem = false;

            // 如果类型相同
            if (eventType1.equals(eventType2)) {
                // 判断各个字段
                for (int i = 0; i < list1.size(); i++) {
                    String key1 = (String)list1.get(i).getName();
                    String key2 = (String)list2.get(i).getName();

                    if (key1.equals("_addr")) {
                        continue;
                    }
                    else if (key1.equals("price")) {
                        BigInteger data1 = (BigInteger) list1.get(i).getData();
                        BigInteger data2 = (BigInteger) list2.get(i).getData();

                        priceProblem = !data1.equals(data2);

                    }
                    else  {
                        String data1 = (String)list1.get(i).getData();
                        String data2 = (String)list2.get(i).getData();
                        if (!data1.equals(data2)) {
                            res.put("res", "false");
                            return JSON.toJSONString(res);
                        }
                    }
                }

                // 如果价格不同
                if (priceProblem) {
                    res.put("res", "true");
                }
            } else {
                res.put("res", "false");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BaseException e) {
            e.printStackTrace();
        }

        return JSON.toJSONString(res);
    }

}
