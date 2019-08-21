package org.fisco.bcos.service;

import org.fisco.bcos.model.CommonResult;
import org.fisco.bcos.model.SceneOrder;
import org.fisco.bcos.model.TrustTravel;
import org.fisco.bcos.utils.HelpUtils;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple3;
import org.fisco.bcos.web3j.tuples.generated.Tuple4;
import org.fisco.bcos.web3j.tuples.generated.Tuple5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;

@Service
public class SceneService {
    @Autowired
    private TrustTravel trustTravel;

    private Logger logger = LoggerFactory.getLogger(HotelService.class);


    // 用户订购景点门票
    public CommonResult bookScene(SceneOrder sceneOrder) {
        CommonResult commonResult = new CommonResult("success");
        try {
            TransactionReceipt receipt = trustTravel.bookOrder(sceneOrder.getAddr(), sceneOrder.getProvince(),
                    sceneOrder.getCity(), sceneOrder.getName(), BigInteger.valueOf(sceneOrder.getPrice()),
                    sceneOrder.getOta(), BigInteger.valueOf(sceneOrder.getFlag())).send();
            if (receipt.getStatus().equals("0x0")){
                HashMap hashMap = new HashMap();
                HelpUtils.addTxDetails(hashMap, commonResult, receipt);

                // 添加hash
                trustTravel.setUserSceneOrderTx(sceneOrder.getAddr(), receipt.getTransactionHash()).send();

                logger.info("用户订购成功");
                return commonResult;
            } else {
                logger.info("交易执行发生异常 - 用户订购不成功");
                commonResult.setMessage("order failed");
                return commonResult;
            }
        } catch (Exception e) {
            logger.info("用户订购景点门票 - 交易发生异常 " + e.getMessage());
            e.printStackTrace();
            commonResult.setMessage("server error");
            return commonResult;
        }
    }

    // 获取景点数量
    public CommonResult getSceneOrderCount(String addr) {
        CommonResult commonResult = new CommonResult<>("success");
        try {
            BigInteger count = trustTravel.getUserSceneCount(addr).send();
            HashMap hashMap = new HashMap();
            hashMap.put("count", count.intValue());
            commonResult.setData(hashMap);
            return commonResult;
        } catch (Exception e) {
            logger.info("获取用户景点订单数量 - 异常", e.getMessage());
            commonResult.setMessage("server error");
            return commonResult;
        }
    }

    // 获取景点订单详细信息
    public CommonResult getSceneOrderDetail(String addr, int index) {
        CommonResult commonResult = new CommonResult<>("success");
        try {
            Tuple4<BigInteger, String, String, String> tuple41 = trustTravel.getUserSceneOrdersInfo(BigInteger.valueOf(index), addr).send();
            HashMap hashMap = new HashMap();
            hashMap.put("time", tuple41.getValue1().intValue());
            hashMap.put("OTA", tuple41.getValue2());
            hashMap.put("state", tuple41.getValue3());
            hashMap.put("hash", tuple41.getValue4());

            Tuple4<String, String, String, BigInteger> tuple42 = trustTravel.getUserOtherScene(BigInteger.valueOf(index), addr).send();
            hashMap.put("province", tuple42.getValue1());
            hashMap.put("city", tuple42.getValue2());

            hashMap.put("name", tuple42.getValue3());
            hashMap.put("price", tuple42.getValue4());
            commonResult.setData(hashMap);

            return commonResult;
        } catch (Exception e) {
            logger.info("获取用户景点订单详情信息 - 异常", e.getMessage());
            commonResult.setMessage("server error");
            return commonResult;
        }
    }

    // 给景点评论
    public CommonResult commentScene(String addr, int index, String content, int score) {
        CommonResult commonResult = new CommonResult("success");
        try {
            TransactionReceipt receipt = trustTravel.addCommentForScene(BigInteger.valueOf(index), addr, content, BigInteger.valueOf(score)).send();
            if (receipt.getStatus().equals("0x0")){
                HashMap hashMap = new HashMap();
                HelpUtils.addTxDetails(hashMap, commonResult, receipt);


                // 添加Hash
                trustTravel.setUserSceneOrderCommentTx(addr, BigInteger.valueOf(index),  receipt.getTransactionHash()).send();

                logger.info("用户评论成功");
                return commonResult;
            } else {
                logger.info("交易执行发生异常 - 用户评论不成功 未订购该景点门票");
                commonResult.setMessage("user not order");
                return commonResult;
            }
        } catch (Exception e) {
            logger.info("用户评论景点 - 交易发生异常 " + e.getMessage());
            commonResult.setMessage("server error");
            return commonResult;
        }
    }

    // 获取景点评论
    public CommonResult getSceneOrderComment(String addr, int index) {
        CommonResult commonResult = new CommonResult<>("success");
        try {
            Tuple5<Boolean, String, BigInteger, BigInteger, String> tuple5 = trustTravel.getUserSceneOrdersComment(BigInteger.valueOf(index), addr).send();
            HashMap hashMap = new HashMap();
            hashMap.put("exist", tuple5.getValue1());
            hashMap.put("content", tuple5.getValue2());
            hashMap.put("score", tuple5.getValue3().intValue());
            hashMap.put("time", tuple5.getValue4().intValue());
            hashMap.put("hash", tuple5.getValue5());

            commonResult.setData(hashMap);

            return commonResult;
        } catch (Exception e) {
            logger.info("获取用户景点订单评论信息 - 异常", e.getMessage());
            commonResult.setMessage("server error");
            return commonResult;
        }
    }




}
