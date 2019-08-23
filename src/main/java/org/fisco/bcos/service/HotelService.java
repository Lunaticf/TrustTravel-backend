package org.fisco.bcos.service;

import org.fisco.bcos.model.CommonResult;
import org.fisco.bcos.model.HotelOrder;
import org.fisco.bcos.model.TrustTravel;
import org.fisco.bcos.model.UserExp;
import org.fisco.bcos.utils.HelpUtils;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple3;
import org.fisco.bcos.web3j.tuples.generated.Tuple4;
import org.fisco.bcos.web3j.tuples.generated.Tuple5;
import org.fisco.bcos.web3j.tuples.generated.Tuple6;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;

@Service
public class HotelService {

    @Autowired
    private TrustTravel trustTravel;

    @Autowired
    private UserExp userExp;

    private Logger logger = LoggerFactory.getLogger(HotelService.class);


    // 用户订购
    public CommonResult bookHotel(HotelOrder hotelOrder) {
        CommonResult commonResult = new CommonResult("success");
        try {
            TransactionReceipt receipt = trustTravel.initializeOrder(hotelOrder.getAddr(),hotelOrder.getDetailAddr(), hotelOrder.getHotel(),
                    hotelOrder.getRoomType(), hotelOrder.getFromDate(), hotelOrder.getToDate(),
                    hotelOrder.getOta(), BigInteger.valueOf(hotelOrder.getTotalPrice()), BigInteger.valueOf(hotelOrder.getFlag())).send();


            if (receipt.getStatus().equals("0x0")){
                HashMap hashMap = new HashMap();
                HelpUtils.addTxDetails(hashMap, commonResult, receipt);

                // 添加hash
                trustTravel.setUserOrderTx(hotelOrder.getAddr(), receipt.getTransactionHash()).send();

                // 先获取用户姓名
                String username = trustTravel.getUserName(hotelOrder.getAddr()).send();

                // 更新积分
                userExp.update_exp(username, BigInteger.valueOf(hotelOrder.getTotalPrice())).send();

                logger.info("用户订购成功");
                return commonResult;
            } else {
                logger.info("交易执行发生异常 - 用户订购不成功");
                commonResult.setMessage("order failed");
                return commonResult;
            }
        } catch (Exception e) {
            logger.info("用户订购房间 - 交易发生异常 " + e.getMessage());
            e.printStackTrace();
            commonResult.setMessage("server error");
            return commonResult;
        }
    }

    // 获取酒店数量
    public CommonResult getHotelOrderCount(String addr) {
        CommonResult commonResult = new CommonResult<>("success");
        try {
            BigInteger count = trustTravel.getUserOrdersCount(addr).send();
            HashMap hashMap = new HashMap();
            hashMap.put("count", count.intValue());
            commonResult.setData(hashMap);
            return commonResult;
        } catch (Exception e) {
            logger.info("获取用户酒店订单数量 - 异常", e.getMessage());
            commonResult.setMessage("server error");
            return commonResult;
        }
    }

    // 获取酒店订单详细信息
    public CommonResult getHotelOrderDetail(String addr, int index) {
        CommonResult commonResult = new CommonResult<>("success");
        try {
            Tuple4<BigInteger, String, String, String> tuple4 = trustTravel.getUserOrdersInfo(BigInteger.valueOf(index), addr).send();
            HashMap hashMap = new HashMap();
            hashMap.put("time", tuple4.getValue1().intValue());
            hashMap.put("OTA", tuple4.getValue2());
            hashMap.put("state", tuple4.getValue3());
            hashMap.put("state", tuple4.getValue3());
            hashMap.put("hash", tuple4.getValue4());

            Tuple5<String, String, String, String, BigInteger> tuple5 = trustTravel.getUserOrdersRoom(BigInteger.valueOf(index), addr).send();
            hashMap.put("hotel", tuple5.getValue1());
            hashMap.put("roomType", tuple5.getValue2());

            hashMap.put("fromDate", tuple5.getValue3());
            hashMap.put("toDate", tuple5.getValue4());
            hashMap.put("totalPrice", tuple5.getValue5());
            commonResult.setData(hashMap);

            return commonResult;
        } catch (Exception e) {
            logger.info("获取用户酒店订单详情信息 - 异常", e.getMessage());
            commonResult.setMessage("server error");
            return commonResult;
        }
    }

    // 给酒店评论
    public CommonResult commentHotel(String addr, int index, String content, int score) {
        CommonResult commonResult = new CommonResult("success");
        try {
            TransactionReceipt receipt = trustTravel.addCommentForHotel(BigInteger.valueOf(index), addr, content, BigInteger.valueOf(score)).send();
            if (receipt.getStatus().equals("0x0")){
                HashMap hashMap = new HashMap();
                HelpUtils.addTxDetails(hashMap, commonResult, receipt);

                trustTravel.setUserOrderCommentTx(addr, BigInteger.valueOf(index),  receipt.getTransactionHash()).send();

                logger.info("用户评论成功");
                return commonResult;
            } else {
                logger.info("交易执行发生异常 - 用户评论不成功 未订购酒店");
                commonResult.setMessage("user not order");
                return commonResult;
            }
        } catch (Exception e) {
            logger.info("用户评论房间 - 交易发生异常 " + e.getMessage());
            commonResult.setMessage("server error");
            return commonResult;
        }
    }

    // 获取酒店评论
    public CommonResult getHotelOrderComment(String addr, int index) {
        CommonResult commonResult = new CommonResult<>("success");
        try {
            Tuple5<Boolean, String, BigInteger, BigInteger, String> tuple5 = trustTravel.getUserOrdersComment(BigInteger.valueOf(index), addr).send();
            HashMap hashMap = new HashMap();
            hashMap.put("exist", tuple5.getValue1());
            hashMap.put("content", tuple5.getValue2());
            hashMap.put("score", tuple5.getValue3().intValue());
            hashMap.put("time", tuple5.getValue4().intValue());
            hashMap.put("hash", tuple5.getValue5());



            commonResult.setData(hashMap);

            return commonResult;
        } catch (Exception e) {
            logger.info("获取用户酒店订单评论信息 - 异常", e.getMessage());
            commonResult.setMessage("server error");
            return commonResult;
        }
    }
}
