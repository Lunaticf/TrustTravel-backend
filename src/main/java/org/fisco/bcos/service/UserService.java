package org.fisco.bcos.service;

import org.fisco.bcos.model.CommonResult;
import org.fisco.bcos.model.TrustTravel;
import org.fisco.bcos.model.User;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;

@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private TrustTravel trustTravel;

    // 为新注册用户生成一个地址
    public static String genAddress() {
        //创建普通外部账户
        EncryptType.encryptType = 0;
        //创建国密外部账户，向国密区块链节点发送交易需要使用国密外部账户
        // EncryptType.encryptType = 1;
        Credentials credentials = GenCredential.create();
        //账户地址
        String address = credentials.getAddress();
        //账户私钥
        String privateKey = credentials.getEcKeyPair().getPrivateKey().toString(16);
        //账户公钥
        String publicKey = credentials.getEcKeyPair().getPublicKey().toString(16);
        return address;
    }

    // 注册用户
    public CommonResult registerUser(User user) {
        CommonResult commonResult = new CommonResult("success");
        try {
            String address = genAddress();
            logger.info("生成随机地址: " + address);
            TransactionReceipt receipt = trustTravel.UserRegister(user.getUsername(), user.getPassword(), address).send();
            // 如果交易执行成功
            System.out.println(receipt);
            if (receipt.getStatus().equals("0x0")){
                logger.info("用户注册成功");
                return commonResult;
            } else {
                logger.info("交易执行发生异常 - 用户名已注册");
                commonResult.setMessage("user existed");
                return commonResult;
            }
        } catch (Exception e) {
            logger.info("用户注册 - 交易发生异常", e.getMessage());
            commonResult.setMessage("server error");
            return commonResult;
        }
    }

    // 获取用户地址
    public CommonResult<String> getUserAddress(String username) {
        CommonResult<String> commonResult = new CommonResult<>("success");
        try {
            String addr = trustTravel.GetUserAddress(username).send();
            // 可能用户名不存在 未注册
            if (addr != null){
                commonResult.setData(addr);
                return commonResult;
            } else {
                commonResult.setMessage("user not exist");
                return commonResult;
            }
        } catch (Exception e) {
            logger.info("获取用户地址 - 交易发生异常", e.getMessage());
            commonResult.setMessage("server error");
            return commonResult;
        }
    }

    // 用户登录
    public CommonResult userLogin(User user) {
        CommonResult commonResult = new CommonResult<>("success");
        try {
            Tuple3<Boolean, String, String> tuple3 = trustTravel.UserLogin(user.getUsername(), user.getPassword()).send();
            // 用户名密码错误
            if (!tuple3.getValue1()){
                commonResult.setMessage("username or password error");
                return commonResult;
            } else {
                HashMap hashMap = new HashMap();
                hashMap.put("addr", tuple3.getValue3());
                commonResult.setData(hashMap);
                return commonResult;
            }
        } catch (Exception e) {
            logger.info("用户登录 - 交易发生异常", e.getMessage());
            commonResult.setMessage("server error");
            return commonResult;
        }
    }

    // 获取用户余额
    public CommonResult getUserBalance(String addr) {
        CommonResult commonResult = new CommonResult<>("success");
        try {
            BigInteger bigInteger = trustTravel.getUserMoney(addr).send();
            HashMap hashMap = new HashMap();
            hashMap.put("balance", bigInteger.toString(10));
            commonResult.setData(hashMap);
            return commonResult;
        } catch (Exception e) {
            logger.info("获取用户余额 - 交易发生异常", e.getMessage());
            commonResult.setMessage("server error");
            return commonResult;
        }
    }
}
