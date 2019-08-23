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

#### 部署的合约地址： 0xd46f3a6941dba65aabc968ed46cd8db224b7e1f2
#### 积分合约地址：0xb92d2c6f861f177692987fc3e30d81cc9b151abf


Tasks

- [x] 编译与部署TurstTravel智能合约

- [x] docapijs 生成文档

- [x] postman测试接口
    
- [x] 登录防止了用户名重复 已完成 修改了智能合约 添加require

- [x] 设计 完成用户注册登录接口 测试成功

- [x] 获取用户余额

- [x] 删掉了智能合约冗余的方法、变量

- [x] 用户订购酒店 获取酒店订单

- [x] 用户评论酒店

- [x] 用户订购景点 评论景点

- [x] 后端设置跨域

- [] 监管节点 积分查询 举证流程 抽奖等业务合约透明

- [] 杀熟对比 获得用户全平台记录 更有利于鉴别恶意用户

- [] 需要解析交易数据 event

- [] 订购酒店与景点的同时添加积分

### Tech Problem
 
- TransactionReceipt 没有合约写操作返回值 建议使用event log 在receipt中解析比较麻烦
- TransactionReceipt的status即使是0，也会成功执行。与eip不符，观察发现status 0x0为成功，0x16为失败
- springmvc-@RequestBody无法映射首字母大写的属性 踩了好久的坑。。
- 前后端跨域问题联调
- 其实智能合约添加数据的时候方法返回数据是很难解析的。。。不要返回数据。直接让交易执行失败
- 问题。。。用户购买服务而不评论的情况怎么办。。。你需要索引啊 比如获取一个酒店订单的时候获取它的评论。。。
## Design Problem
- 酒店与景点数据在前端模拟？
- 关于订单的状态 我之前写的APP设想的是一个流程，用户发起后销售方进行再次确认，两次确认都在链上记录 所以分了两个状态 这里先不管
- 查询订单的时候 后端会多返回一个时间戳 前端自行转换


