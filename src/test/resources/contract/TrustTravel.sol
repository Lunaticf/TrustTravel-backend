pragma solidity ^0.4.24;

import "./Table.sol";

contract TrustTravel {

    //景点信息
    struct SceneInfo{
        string province;    //省
        string city;        //市
        string S_name;      //景点名字
        uint S_price;       //门票
    }
    
    // 酒店房间
    struct Room {
        string detailaddr;   //酒店详细地址
        string hotel;       // 酒店名
        string roomType;    // 房间类型
        string fromDate;    // 入住日期 2018-11-3
        string toDate;      // 离开日期 2018-11-5
        uint totalPrice;    // 总价格     
    }
    
    //评论信息
    struct Comment {
        uint time;        //评论时间
        string content;     //评语
        uint score;         //评分
        bool exist;         // 判断是否存在
        string hash;        // 交易hash
    }

    // 用户酒店交易
    struct UserOrder {
        uint time;          // 时间戳 unix
        Room room;          // 订购房间
        string OTA;         // 订购的平台
        string state;       // 表明订单状态：init/confirmed
        uint flag;          //设置整型，定义交易状态
        Comment comment;    // 评论
        string hash;        // 交易hash
    }
    
    // 用户预订旅游门票
    struct UserSceneOrder {
        uint time;          //时间戳
        SceneInfo sceneInfo;//预定景点信息
        string OTA;         //预定的平台
        string state;       //订单状态
        uint flag1;         //设置整型，定义交易状态
        Comment comment;     // 评论
        string hash;        // 交易hash
    }

    struct User {
        string userName;
        //uint passwd;
        uint Owner_money;
        UserOrder[] orders;
        UserSceneOrder[] orders1;
    }
    
    struct UserInfo {
        string passwd;
        //用户积分
        uint UserExp;
        address addr;
    }
    
    address[] UserAddress;
    
    //计算用户对服务商的评分
    uint sum = 0;
    uint sum1 = 0;
    uint a = 0;
    uint b = 0;

    //mapping (address => Comment) public commentInfo;
    mapping (address => User) public userInfo;

    // 用户名对应用户的信息
    mapping (string => UserInfo) Login;

    //监控用户
    event Users(string username, address addr);
    event RegisterEvent(int256 ret_code, string username, uint256 user_exp);
    //事件监控
    event BookingHotel(string username, address _addr, string _detailaddr, string hotel, uint price);
    event BookingScene(string username, address _addr, string s_name, uint price);
    event CommentsInfo(address _addr, uint _idx, string content, uint score);
    event UpdateExp(string username, uint exp);

    constructor() public  { 
        //构造函数中创建t_user_exp表
        createTable();
    }
    
    function createTable() private {
        TableFactory tf = TableFactory(0x1001);
        // 积分管理表, key : username, field : user_exp
        // |    用户名(主键)     |     用户积分      |
        // |-------------------- |-------------------|
        // |        username     |    user_exp       |     
        // |---------------------|-------------------|
        //
        // 创建表
        tf.createTable("t_user_exp", "username", "user_exp");
    }
    
    function openTable() private returns(Table) {
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_user_exp");
        return table;
    }
    
    /*
    描述 : 根据用户名查询用户积分(实现全平台通用积分系统，后续可设置积分兑换机制)
    参数 ： 
            username : 用户名
    返回值：
            参数一： 成功返回0, 用户名不存在返回-1
            参数二： 第一个参数为0时有效，积分数
    */
    function select(string username) public constant returns(int256, uint256) {
        // 打开表
        Table table = openTable();
        // 查询
        Entries entries = table.select(username, table.newCondition());
        uint256 user_exp = 0;
        if (0 == uint256(entries.size())) {
            return (-1, user_exp);
        } else {
            Entry entry = entries.get(0);
            return (0, uint256(entry.getInt("user_exp")));
        }
    }
    
    /*
    描述 : 积分初始化
    参数 ： 
            username  : 用户名
            user_exp  : 用户积分
    返回值：
            0  初始化成功
            -1 用户名已存在
            -2 其他错误
    */
    function set_user_exp(string username, uint256 user_exp) public returns(int256){
        int256 ret_code;
        int256 ret= 0;
        uint256 temp_user_exp = 0;
        // 查询用户名是否存在
        (ret, temp_user_exp) = select(username);
        if(ret != 0) {
            Table table = openTable();
            
            Entry entry = table.newEntry();
            entry.set("username", username);
            entry.set("user_exp", int256(user_exp));
            // 插入
            int count = table.insert(username, entry);
            if (count == 1) {
                // 成功
                ret_code = 0;
            } else {
                // 失败? 无权限或者其他错误
                ret_code = -2;
            }
        } else {
            // 用户名已存在
            ret_code = -1;
        }
        emit RegisterEvent(ret_code, username, user_exp);
        return ret_code;
    }
    
    //更新积分
    function update_exp(string username, uint256 exp) public{
        uint256 pre_exp = 0;
        int256 ret = 0;
        (ret, pre_exp) = select(username);
        Table table = openTable();
        Entry entry = table.newEntry();
        entry.set("username", username);
        entry.set("user_exp", int256(pre_exp + exp));
        //更新用户积分
        table.update(username, entry, table.newCondition());
        emit UpdateExp(username, exp);
    }
    
    //用户注册
    function UserRegister(string memory username, string memory passwd, address _addr) public returns(int256, bool, string memory, string memory){
        require(
            Login[username].addr == address(0)
        );
        Login[username].passwd = passwd;
        Login[username].addr = _addr;
        setUserMoney(_addr);
        UserAddress.push(_addr);
        emit Users(username, _addr);
        int ret_code = set_user_exp(username, 0);
        if (ret_code == 0){
            return(ret_code, true, username, "Register successful!");
        }
        return(ret_code, false, username, "Register failed!");
    }

    // solidity比较字符串方式
    function compareStringsbyBytes(string memory s1, string memory s2) public pure returns(bool){
        return keccak256(abi.encodePacked(s1)) == keccak256(abi.encodePacked(s2));
    }
    
    //用户登录
    function UserLogin(string memory username, string memory passwd) view public returns(bool, string memory, address){
        if (compareStringsbyBytes(Login[username].passwd,passwd)) {
            return(true, "Login successful", Login[username].addr);
        }else{
            //假设这是一个假的地址.
            return(false, "Login fail", 0x79a7A47806D2dfee07b42662C4F65816461d14d2);
        }
    }
    
    //获取用户信息
    function GetUserAddress(string memory username) view public returns(address) {
        return Login[username].addr;
    }

    //初始化用户的余额
    function setUserMoney(address _addr) private{
        //管理者给每个初始用户10000的金额作为本金
        userInfo[_addr].Owner_money = 10000;
    }
    

    //预定景点门票
    function bookOrder(string memory username, address _addr, string memory _province, string memory _city, string memory s_name, uint s_price, string memory _OTA, uint f1) public{
        //require(passwd == userInfo[_addr].passwd && username == userInfo[_addr].username);
        SceneInfo memory sceneInfo = SceneInfo(_province, _city, s_name, s_price);
        Comment memory comment = Comment(0, "", 0, false, "");

        UserSceneOrder memory userSceneOrder = UserSceneOrder(now, sceneInfo, _OTA, "initialization", f1, comment, "");
        userInfo[_addr].orders1.push(userSceneOrder);
        userInfo[_addr].Owner_money -= s_price;
        
        emit BookingScene(username, _addr, s_name, s_price);
        //购物即赋予一定的积分，积分规则为1元=1积分
        uint exp = s_price;
        update_exp(username, exp);
    }


    // 订购酒店房间
    function initializeOrder(string memory username, address _addr, string memory _detailaddr, string memory _hotel, string memory _roomType, string memory _fromDate, string memory _toDate, string memory _OTA, uint _totalPrice, uint f2) public{ 
        //require(passwd == userInfo[_addr].passwd && username == userInfo[_addr].username);
        Room memory room = Room(_detailaddr, _hotel, _roomType, _fromDate, _toDate, _totalPrice);
        Comment memory comment = Comment(0, "", 0, false, "");
        
        UserOrder memory userOrder = UserOrder(now, room, _OTA, "initialization", f2, comment, "");
        userInfo[_addr].orders.push(userOrder);
        userInfo[_addr].Owner_money -= _totalPrice;
        
        emit BookingHotel(username, _addr, _detailaddr, _hotel, _totalPrice);
        uint exp = _totalPrice;
        update_exp(username, exp);
    }
    
    //对酒店订单进行评价
    function addCommentForHotel(uint _idx, address _addr, string memory content, uint score) public {
        require(
            userInfo[_addr].orders[_idx].flag == 1
        );
        Comment memory comment = Comment(now, content, score, true, "");
        userInfo[_addr].orders[_idx].comment = comment;
        
        emit CommentsInfo(_addr, _idx, content, score);
    }

    
    //对景点订单进行评价
    function addCommentForScene(uint _idx, address _addr, string memory content, uint score) public{
        require(
            userInfo[_addr].orders1[_idx].flag1 == 1
        );
        Comment memory comment = Comment(now, content, score, true, "");
        userInfo[_addr].orders1[_idx].comment = comment;
        
        emit CommentsInfo(_addr, _idx, content, score);
    }

    // 获得酒店评论
    function getUserOrdersComment(uint _idx, address _addr) public view returns (bool, string memory, uint, uint, string){
        bool  _exist =   userInfo[ _addr].orders[_idx].comment.exist;
        string storage _content = userInfo[ _addr].orders[_idx].comment.content;

        uint _score = userInfo[ _addr].orders[_idx].comment.score;
        uint  _commentTime = userInfo[ _addr].orders[_idx].comment.time;
        string storage  _hash = userInfo[ _addr].orders[_idx].comment.hash;
        return (_exist, _content, _score, _commentTime, _hash);
    }

    function getUserSceneOrdersComment(uint _idx, address _addr) public view returns (bool, string memory, uint, uint, string){
        bool  _exist =   userInfo[ _addr].orders1[_idx].comment.exist;
        string storage _content = userInfo[ _addr].orders1[_idx].comment.content;

        uint _score = userInfo[ _addr].orders1[_idx].comment.score;
        uint  _commentTime = userInfo[ _addr].orders1[_idx].comment.time;
        string storage  _hash = userInfo[ _addr].orders1[_idx].comment.hash;
        return (_exist, _content, _score, _commentTime, _hash);
    }
    
    //获取酒店评分均值
    function getHotelScore(string memory _detailaddr, string memory _name) public returns(string memory, string memory, uint){
        for(uint i=0; i<UserAddress.length;i++){
            for(uint j=0; j<userInfo[UserAddress[i]].orders.length;j++){
                if (compareStringsbyBytes(userInfo[UserAddress[i]].orders[j].room.hotel, _name) && compareStringsbyBytes(userInfo[UserAddress[i]].orders[j].room.detailaddr, _detailaddr)){
                    sum += userInfo[UserAddress[i]].orders[j].comment.score;
                    a++;
                }
            }
        }
        uint ave = sum/a;
        sum = 0;
        a = 0;
        return (_detailaddr, _name, ave);
    }

    
    //获取景点评分均值
    function getSceneScore(string memory _name) public returns(string memory, uint){
        for(uint i=0; i<UserAddress.length; i++){
            for(uint j =0; j<userInfo[UserAddress[i]].orders1.length;j++){
                if (compareStringsbyBytes(userInfo[UserAddress[i]].orders1[j].sceneInfo.S_name, _name)){
                    sum1 += userInfo[UserAddress[i]].orders1[j].comment.score;
                    b++;
                }
            }
        }
        uint ave = sum1/b;
        sum1 = 0;
        a = 0;
        return (_name, ave);
    }

    // solidity 不能返回结构体，更不能返回结构体数组...
    // 所以这里的操作有点麻烦
    // 得到用户订单的附加信息 用户订单分为info(userOrder结构体里的其他信息)和room
    function getUserOrdersInfo(uint _idx, address _addr) public view returns (uint, string memory, string memory, string memory){
       uint _time =  userInfo[ _addr].orders[_idx].time;
       string storage _OTA = userInfo[ _addr].orders[_idx].OTA;
       string storage _state = userInfo[ _addr].orders[_idx].state;
       string storage _hash = userInfo[ _addr].orders[_idx].hash;

       return (_time, _OTA, _state, _hash);
    }
    
    function getUserSceneOrdersInfo(uint _idx, address _addr) public view returns(uint, string memory, string memory, string memory){
        uint _time =  userInfo[ _addr].orders1[_idx].time;
        string storage _OTA = userInfo[ _addr].orders1[_idx].OTA;
        string storage _state = userInfo[ _addr].orders1[_idx].state;
        string storage _hash = userInfo[ _addr].orders[_idx].hash;

        return (_time, _OTA, _state, _hash);
    }

    // 得到用户订单的酒店信息
    function getUserOrdersRoom(uint _idx, address _addr) public view returns (string memory, string memory, string memory, string memory, string memory, uint){
        string storage _detailaddr = userInfo[_addr].orders[_idx].room.detailaddr;
        string storage _hotel = userInfo[_addr].orders[_idx].room.hotel;
        string storage _roomType = userInfo[_addr].orders[_idx].room.roomType;
        string storage _fromDate = userInfo[_addr].orders[_idx].room.fromDate;
        string storage _toDate = userInfo[_addr].orders[_idx].room.toDate;
        uint _totalPrice = userInfo[_addr].orders[_idx].room.totalPrice;
        
        return (_detailaddr, _hotel, _roomType, _fromDate, _toDate, _totalPrice);
    }
    
    //得到用户订单的景点信息
    function getUserOtherScene(uint _idx, address _addr) public view returns (string memory, string memory, string memory, uint){
        string storage _province = userInfo[_addr].orders1[_idx].sceneInfo.province;
        string storage _city = userInfo[_addr].orders1[_idx].sceneInfo.city;
        string storage _name = userInfo[_addr].orders1[_idx].sceneInfo.S_name;
        uint _price = userInfo[_addr].orders1[_idx].sceneInfo.S_price;
        return(_province, _city, _name, _price);
    }

    //获取用户的余额
    function getUserMoney(address _addr) public view returns (uint) {
        return userInfo[_addr].Owner_money;
    }

    // 得到用户酒店订单数量
    function getUserOrdersCount(address _addr) public view returns(uint) {
        return userInfo[_addr].orders.length;
    }
    
    //得到用户景点订单数量
    function getUserSceneCount(address _addr) public view returns(uint) {
        return userInfo[_addr].orders1.length;
    }

    // 设置用户酒店订单Hash
    function setUserOrderTx(address _addr, uint _idx, string memory txHash) public {
        userInfo[_addr].orders[_idx].hash = txHash;
    }

    // 设置用户酒店评论Hash
    function setUserOrderCommentTx(address _addr, uint _idx, string memory txHash) public {
        userInfo[_addr].orders[_idx].comment.hash = txHash;
    }

    // 设置用户旅游订单hash
    function setUserSceneOrderTx(address _addr, uint _idx, string memory txHash) public {
        userInfo[_addr].orders1[_idx].hash = txHash;
    }

    // 设置用户旅游评论hash
    function setUserSceneOrderCommentTx(address _addr, uint _idx, string memory txHash) public {
        userInfo[_addr].orders1[_idx].comment.hash = txHash;
    }
}