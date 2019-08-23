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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
    @PostMapping("/legal/tx")
    public HotelEvent decoderHotelTx(@RequestBody String json) {
        JSONObject parse = JSON.parseObject(json);
        String hash = (String)parse.get("hash");
        System.out.println(hash);

        try {
            BcosTransactionReceipt receipt = web3j.getTransactionReceipt(hash).send();
            Optional<TransactionReceipt> transactionReceipt = receipt.getTransactionReceipt();
            List<Log> logs =
                    transactionReceipt.get().getLogs();
            String eventJson = HelpUtils.getTxDecoder4Travel().decodeEventReturnJson(logs);
            System.out.println(eventJson);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BaseException e) {
            e.printStackTrace();
        }


        return new HotelEvent();
    }

}
