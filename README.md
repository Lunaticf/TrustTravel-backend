## TrustTravel backend

用IDEA打开
gradle可能需要重新import一下

```
./gradlew build
./gradlew test
```


当所有测试案例运行成功，则代表区块链运行正常，该项目通过SDK连接区块链正常。开发者可以基于该项目进行具体应用开发。


idea直接运行src/test/java/org/fisco/bcos/下的ContractTest
deployAndCallHelloWorld方法
junit绿色就是成功了
```
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
```

#### 部署的合约地址： 0x8d2fdc1a7f1fa01facecf369bf9fe65d467a6729



Tasks

- [x] 编译与部署TurstTravel智能合约
    
- [x] 登录防止了用户名重复 已完成 修改了智能合约 添加require

- [x] 测试用户注册登录

- [] 测试用户订购酒店 景点

### Tech Problem
 
- TransactionReceipt 没有合约写操作返回值 建议使用event log 在receipt中解析比较麻烦
- TransactionReceipt的status即使是0，也会成功执行。与eip不符，观察发现status 0x0为成功，0x16为失败

### Design Problem
- 酒店与景点数据在前端模拟？