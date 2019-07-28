package org.fisco.bcos;

import static org.junit.Assert.assertTrue;

import org.fisco.bcos.constants.GasConstants;
import org.fisco.bcos.temp.HelloWorld;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ContractTest extends BaseTest {

    @Autowired private Web3j web3j;
    @Autowired private Credentials credentials;

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
        String contractAddr = "0x7a519f571f028074d7929be5456cedcb6be9c424";
        HelloWorld helloWorld = HelloWorld.load(contractAddr, web3j, credentials, new StaticGasProvider(
                                        GasConstants.GAS_PRICE, GasConstants.GAS_LIMIT));

        if (helloWorld != null) {
            System.out.println("HelloWorld address is: " + helloWorld.getContractAddress());
            // call set function
//            helloWorld.set("Hello, World!").send();
            // call get function
            String result = helloWorld.get().send();
            System.out.println(result);
            assertTrue("hello trustTravel".equals(result));
        }
    }
}
