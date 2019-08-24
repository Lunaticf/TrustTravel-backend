## TrustTravel

### 项目说明
基于FISCO BCOS2.0的TrustTravel项目后端代码

基于Spring boot和FISCO BCOS提供的[Web3SDK](https://fisco-bcos-documentation.readthedocs.io/en/latest/docs/sdk/sdk.html) 开发

## 快速启动

### 前置条件
搭建FISCO BCOS区块链，具体步骤[参考这里](https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/installation.html)。

### 获取源码

```
$ git clone https://github.com/Lunaticf/TrustTravel-backend
```

#### 节点证书配置
将所需节点所在目录`nodes/${ip}/sdk`下的`ca.crt`、`node.crt`和`node.key`文件拷贝到项目的`src/main/resources`目`录下供SDK使用。

### 配置文件设置

spring boot项目的配置文件application.yml如下图所示，其中加了注释的内容根据区块链节点配置做相应修改。
  
```yml
encrypt-type: # 0：普通， 1：国密
 encrypt-type: 0 
 
group-channel-connections-config:
  all-channel-connections:
  - group-id: 1  # 群组ID
    connections-str:
                    - 127.0.0.1:20200  # 节点，listen_ip:channel_listen_port
                    - 127.0.0.1:20201
  - group-id: 2  
    connections-str:
                    - 127.0.0.1:20202  # 节点，listen_ip:channel_listen_port
                    - 127.0.0.1:20203
 
channel-service:
  group-id: 1 # sdk实际连接的群组
  agency-name: TrustTravel # 机构名称

accounts:
  pem-file: 0xcdcce60801c0a2e6bb534322c32ae528b9dec8d2.pem # PEM 格式账户文件
  p12-file: 0x98333491efac02f8ce109b0c499074d47e7779a6.p12 # PKCS12 格式账户文件
  password: 123456 # PKCS12 格式账户密码
```
项目中关于SDK配置的详细说明请[参考这里](https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/sdk/sdk.html#sdk)。

### 运行

编译并运行测试案例，在项目根目录下运行：
```
$ cd spring-boot-starter
$ ./gradlew build
$ ./gradlew test
```

当所有测试案例运行成功，则代表区块链运行正常，TrustTravel项目通过SDK连接区块链正常。

**注：如果在IntelliJ IDEA或Eclipse中运行该demo工程，则使用gradle wrapper模式并安装[Lombok插件](https://projectlombok.org/setup/intellij)，此外IntelliJ IDEA需要在设置中开启`Annotation Processors`功能。**

### 当前主要模块
参考src/controller等相关代码
- Hotel
- Scene
- User
- Regulator



