package org.fisco.bcos;

import static org.junit.Assert.assertTrue;

import org.fisco.bcos.constants.GasConstants;
import org.fisco.bcos.service.UserService;
import org.fisco.bcos.temp.HelloWorld;
import org.fisco.bcos.temp.TrustTravel;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple3;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class ContractTest extends BaseTest {

    @Autowired private Web3j web3j;
    @Autowired private Credentials credentials;
    @Autowired private UserService userService;

    @Test
    public void deployAndCallHelloWorld() throws Exception {
        // deploy contract
//        HelloWorld helloWorld =
//                HelloWorld.deploy(
//                                web3j,
//                                credentials,
//                                new StaticGasProvider(
//                                        GasConstants.GAS_PRICE, GasConstants.GAS_LIMIT))
//                        .send();

        // load 服务器上bcos的hello world合约
        String contractAddr = "0x89bb0e5df40dba9f9e956e8f2f9c254f7d5c33b0";
        HelloWorld helloWorld = HelloWorld.load(contractAddr, web3j, credentials, new StaticGasProvider(
                                        GasConstants.GAS_PRICE, GasConstants.GAS_LIMIT));

        if (helloWorld != null) {
            System.out.println("HelloWorld address is: " + helloWorld.getContractAddress());
            // call set function
//            helloWorld.set("Hello, World!").send();
            // call get function
            TransactionReceipt receipt = helloWorld.set("fuck").send();
            System.out.println(receipt);
            System.out.println(helloWorld.get().send());
        }
    }

    @Test
    public void deployTrustTravel() throws Exception {
        TrustTravel trustTravel =
                TrustTravel.deploy(
                                web3j,
                                credentials,
                                new StaticGasProvider(
                                        GasConstants.GAS_PRICE, GasConstants.GAS_LIMIT))
                        .send();
        System.out.println(trustTravel.getContractAddress());
    }

    @Test
    public void CallTrustTravel() throws Exception {
        String contractAddr = "0x8d2fdc1a7f1fa01facecf369bf9fe65d467a6729";
        TrustTravel trustTravel = TrustTravel.load(contractAddr, web3j, credentials, new StaticGasProvider(
                GasConstants.GAS_PRICE, GasConstants.GAS_LIMIT));

        // 为用户生成一个地址
        String address = userService.genAddress();
        System.out.println("生成的用户地址： " + address);

        // call user register
        TransactionReceipt receipt = trustTravel.UserRegister("alice", "123456", address).send();
        System.out.println(receipt);
        // 0x1代表成功
        //Assert.assertEquals(receipt.getStatus(), "0x1");

        // 重复注册应该不成功
        receipt = trustTravel.UserRegister("alice", "123456", address).send();
        System.out.println(receipt);
        // 0x1代表成功
        //Assert.assertEquals(receipt.getStatus(), "0x1");

//        String retAddress = trustTravel.GetUserAddress("alice").send();
//        System.out.println(retAddress);
//        // get user address
//        Assert.assertEquals(address, retAddress);

        // user login true
        Tuple3<Boolean, String, String> res = trustTravel.UserLogin("alice", "123456").send();
        Assert.assertTrue(res.getValue1());

        // user login false
        // 密码错误
        res = trustTravel.UserLogin("alice", "123213").send();
        Assert.assertFalse(res.getValue1());

    }


}
