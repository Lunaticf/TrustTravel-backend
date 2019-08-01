pragma solidity ^0.4.24;

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
        string hotel;       // 酒店名
        string roomType;    // 房间类型
        string fromDate;    // 入住日期 2018-11-3
        string toDate;      // 离开日期 2018-11-5
        uint totalPrice;    // 总价格     
    }
    
    //酒店评论信息
    struct Comment {
        uint time;        //评论时间
        string comment;     //评语
        uint score;         //评分
        //PendingOrder pendingOrder;
    }
    
    //景点评论信息
    struct Comment1 {
        uint time;          //评论时间
        string comment;     //评语
        uint score;         //评分
    }

    // 用户酒店交易
    struct UserOrder {
        uint time;          // 时间戳 unix
        Room room;          // 订购房间
        //SceneInfo sceneInfo;//预定景点信息
        string OTA;         // 订购的平台
        string state;       // 表明订单状态：init/comfirmed
        uint flag;          //设置整型，定义交易状态
    }
    
    // 用户预订旅游门票
    struct UserSceneOrder {
        uint time;          //时间戳
        SceneInfo sceneInfo;//预定景点信息
        string OTA;         //预定的平台
        string state;       //订单状态
        uint flag1;         //设置整型，定义交易状态
    }

    struct User {
        string userName;
        //uint passwd;
        uint Owner_money;
        UserOrder[] orders;
        UserSceneOrder[] orders1;
        Comment[] comments;
        Comment1[] comments1;
    }

    // 类似于指针，指向用户的订单，只保存地址和索引
    struct PendingOrder {
        address userAddr;
        uint idx;
        //Comment[] commets;
    }
    
    //指向用户的订票信息
    struct PendingSceneOrder {
        address userAddr;
        uint idx;
    }
    
    struct UserInfo {
        string passwd;
        address addr;
    }
    

    //mapping (address => Comment) public commentInfo;
    mapping (address => User) public userInfo;

    // 存储账户类型
    mapping (address => string) public accountType; 

    // 用户名对应用户的信息
    mapping (string => UserInfo) Login;


    constructor() public  { 

    }

    PendingOrder[] pendingPool;
    PendingSceneOrder[] pendingScenePool;

    //User Register
    function UserRegister(string memory username, string passwd, address _addr) public returns(bool, string memory, string memory){
        require(
            Login[username].addr == address(0)
        );
        Login[username].passwd = passwd;
        Login[username].addr = _addr;
        setUserMoney(_addr);
        return(true, username, "Register successful!");
    }

    // solidity比较字符串方式
    function compareStringsbyBytes(string memory s1, string memory s2) public pure returns(bool){
        return keccak256(abi.encodePacked(s1)) == keccak256(abi.encodePacked(s2));
    }
    
    //User Login
    function UserLogin(string memory username, string passwd) view public returns(bool, string memory, address){
        if (compareStringsbyBytes(Login[username].passwd,passwd)) {
            return(true, "Login successful", Login[username].addr);
        }else{
            //假设这是一个假的地址.
            return(false, "Login fail", 0x79a7A47806D2dfee07b42662C4F65816461d14d2);
        }
    }
    
    //getUserInfo
    function GetUserAddress(string memory username) view public returns(address) {
        return Login[username].addr;
    }

    //初始化用户的余额
    function setUserMoney(address _addr) private{
        //管理者给每个初始用户10000的金额作为本金
        userInfo[_addr].Owner_money = 10000;
    }
    
    // 改变用户姓名
    function changeUserName(address _addr, string  memory _name) public {
        userInfo[_addr].userName = _name;
    }

    // 获得用户姓名
    function getUserName(address _addr) public view returns (string memory) {
        return userInfo[_addr].userName;
    }

    // 获得用户身份
    function getAccountType(address _addr) public view returns (string memory) {
        return accountType[_addr];
    }

    //预定景点门票
    function bookOrder(address _addr, string memory _province, string memory _city, string memory s_name, uint s_price, string memory _OTA, uint f1) public {
        //require(passwd == userInfo[_addr].passwd && username == userInfo[_addr].username);
        SceneInfo memory sceneInfo = SceneInfo(_province, _city, s_name, s_price);
        UserSceneOrder memory userSceneOrder = UserSceneOrder(now, sceneInfo, _OTA, "initialization", f1);
        userInfo[_addr].orders1.push(userSceneOrder);
        PendingSceneOrder memory pendingSceneOrder = PendingSceneOrder(_addr, userInfo[_addr].orders1.length - 1);
        pendingScenePool.push(pendingSceneOrder);
        userInfo[_addr].Owner_money -= s_price;
    }

    // 发起订购
    function initializeOrder(address _addr, string memory _hotel, string memory _roomType, string memory _fromDate, string memory _toDate, string memory _OTA, uint _totalPrice, uint f2) public { 
        //require(passwd == userInfo[_addr].passwd && username == userInfo[_addr].username);
        Room memory room = Room(_hotel, _roomType, _fromDate, _toDate, _totalPrice);
        //SceneInfo memory sceneInfo = SceneInfo(_province, _city, s_name, s_price);
        UserOrder memory userOrder = UserOrder(now, room, _OTA, "initialization", f2);
        userInfo[_addr].orders.push(userOrder);

        PendingOrder memory pendingOrder = PendingOrder(_addr,  userInfo[_addr].orders.length - 1);
        // 把订单推到PendingPool;
        pendingPool.push(pendingOrder);
        userInfo[_addr].Owner_money -= _totalPrice;
    }
    
    //对酒店订单进行评价
    function addCommentForHotel(uint _idx, address _addr, uint time, string memory content, uint score) public returns(bool, string memory){
         //判断是否已经预订了
         if (userInfo[_addr].orders[_idx].flag == 1){
             Comment memory comment = Comment(time, content, score);
             userInfo[_addr].comments.push(comment);
             return(true, "successful!");
         }else{
             return(false, "fail!");
         }
        
    }
    
    //获取酒店评论信息
    function getCommentInfoForHotel(uint _idx, address _addr) public view returns (string memory, uint, string memory, uint){
        string storage hotel_name = userInfo[_addr].orders[_idx].room.hotel;
        uint time = userInfo[_addr].comments[_idx].time;
        string memory content = userInfo[_addr].comments[_idx].comment;
        uint score = userInfo[_addr].comments[_idx].score;
        return (hotel_name, time, content, score);
    }
    
    //对景点订单进行评价
    function addCommentForScene(uint _idx, address _addr, uint time, string memory content, uint score) public returns (bool, string memory){
        if (userInfo[_addr].orders1[_idx].flag1 == 1){
            Comment1 memory comment1 = Comment1(time, content, score);
            userInfo[_addr].comments1.push(comment1);
            return(true, "successful!");
        }else{
            return(false, "fail!");
        }
    }
    
    //获取景点评论信息
    function getCommentInfoForScene(uint _idx, address _addr) public view returns(string memory, uint, string memory, uint){
        string storage scene_name = userInfo[_addr].orders1[_idx].sceneInfo.S_name;
        uint time = userInfo[_addr].comments1[_idx].time;
        string memory content = userInfo[_addr].comments1[_idx].comment;
        uint score = userInfo[_addr].comments1[_idx].score;
        return(scene_name, time, content, score);
    }
    
    // solidity 不能返回结构体，更不能返回结构体数组...
    // 所以这里的操作有点麻烦
    // 得到用户订单的附加信息 用户订单分为info(userOrder结构体里的其他信息)和room
    function getUserOrdersInfo(uint _idx, address _addr) public view returns (uint, string memory, string memory){
       uint _time =  userInfo[ _addr].orders[_idx].time;
       string storage _OTA = userInfo[ _addr].orders[_idx].OTA;
       string storage _state = userInfo[ _addr].orders[_idx].state;

       return (_time, _OTA, _state);
    }
    
    function getUserSceneOrdersInfo(uint _idx, address _addr) public view returns(uint, string memory, string memory){
        uint _time = userInfo[_addr].orders1[_idx].time;
        string storage _OTA = userInfo[_addr].orders1[_idx].OTA;
        string storage _state = userInfo[_addr].orders1[_idx].state;
        
        return(_time, _OTA, _state);
    }

    // 得到用户订单的酒店信息
    function getUserOrdersRoom(uint _idx, address _addr) public view returns (string memory, string memory, string memory, string memory, uint){
        string storage _hotel = userInfo[_addr].orders[_idx].room.hotel;
        string storage _roomType = userInfo[_addr].orders[_idx].room.roomType;
        string storage _fromDate = userInfo[_addr].orders[_idx].room.fromDate;
        string storage _toDate = userInfo[_addr].orders[_idx].room.toDate;
        uint _totalPrice = userInfo[_addr].orders[_idx].room.totalPrice;

        return (_hotel, _roomType, _fromDate, _toDate, _totalPrice);
    }
    
    //获取用户的余额
    function getUserMoney(address _addr) public view returns (uint) {
        return userInfo[_addr].Owner_money;
    }
    
    //得到用户订单的景点信息
    function getUserOtherScene(uint _idx, address _addr) public view returns (string memory, string memory, string memory, uint){
        string storage _province = userInfo[_addr].orders1[_idx].sceneInfo.province;
        string storage _city = userInfo[_addr].orders1[_idx].sceneInfo.city;
        string storage _name = userInfo[_addr].orders1[_idx].sceneInfo.S_name;
        uint _price = userInfo[_addr].orders1[_idx].sceneInfo.S_price;
        return(_province, _city, _name, _price);
    }


    // 得到用户酒店订单数量
    function getUserOrdersCount(address _addr) public view returns(uint) {
        return userInfo[_addr].orders.length;
    }
    
    //得到用户景点订单数量
    function getUserSceneCount(address _addr) public view returns(uint) {
        return userInfo[_addr].orders1.length;
    }
    
    //得到用户景点评论数量
    function getUserCommentSceneCount(address _addr) public view returns(uint) {
        return userInfo[_addr].comments1.length;
    }
    
    //得到用户酒店评论数量
    function getUserCommentHotelCount(address _addr) public view returns(uint){
        return userInfo[_addr].comments.length;
    }



}