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
        string content;     //评语
        uint score;         //评分
        bool exist;         // 判断是否存在
    }


    // 用户酒店交易
    struct UserOrder {
        uint time;          // 时间戳 unix
        Room room;          // 订购房间
        //SceneInfo sceneInfo;//预定景点信息
        string OTA;         // 订购的平台
        string state;       // 表明订单状态：init/confirmed
        uint flag;          //设置整型，定义交易状态
        Comment comment;    // 评论
    }
    
    // 用户预订旅游门票
    struct UserSceneOrder {
        uint time;          //时间戳
        SceneInfo sceneInfo;//预定景点信息
        string OTA;         //预定的平台
        string state;       //订单状态
        uint flag1;         //设置整型，定义交易状态
        Comment comment;     // 评论
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
    // User Register
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
    
    // getUserInfo
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
        Comment memory comment = Comment(0, "", 5, false);

        UserSceneOrder memory userSceneOrder = UserSceneOrder(now, sceneInfo, _OTA, "initialization", f1, comment);
        userInfo[_addr].orders1.push(userSceneOrder);
        userInfo[_addr].Owner_money -= s_price;
    }

    // 订购酒店房间
    function initializeOrder(address _addr, string memory _hotel, string memory _roomType, string memory _fromDate, string memory _toDate, string memory _OTA, uint _totalPrice, uint f2) public { 
        //require(passwd == userInfo[_addr].passwd && username == userInfo[_addr].username);
        Room memory room = Room(_hotel, _roomType, _fromDate, _toDate, _totalPrice);
        Comment memory comment = Comment(0, "", 5, false);
        //SceneInfo memory sceneInfo = SceneInfo(_province, _city, s_name, s_price);
        UserOrder memory userOrder = UserOrder(now, room, _OTA, "initialization", f2, comment);
        userInfo[_addr].orders.push(userOrder);

        userInfo[_addr].Owner_money -= _totalPrice;
    }
    
    //对酒店订单进行评价
    function addCommentForHotel(uint _idx, address _addr, string memory content, uint score) public {
        require(
            userInfo[_addr].orders[_idx].flag == 1
        );
        Comment memory comment = Comment(now, content, score, true);
        userInfo[_addr].orders[_idx].comment = comment;
    }

    
    //对景点订单进行评价
    function addCommentForScene(uint _idx, address _addr, string memory content, uint score) public{
        require(
            userInfo[_addr].orders1[_idx].flag1 == 1
        );
        Comment memory comment = Comment(now, content, score, true);
        userInfo[_addr].orders1[_idx].comment = comment;
    }

    // 获得酒店评论
    function getUserOrdersComment(uint _idx, address _addr) public view returns (bool, string memory, uint, uint){
        bool  _exist =   userInfo[ _addr].orders[_idx].comment.exist;
        string storage _content = userInfo[ _addr].orders[_idx].comment.content;

        uint _score = userInfo[ _addr].orders[_idx].comment.score;
        uint  _commentTime = userInfo[ _addr].orders[_idx].comment.time;
        return (_exist, _content, _score, _commentTime);
    }

    function getUserSceneOrdersComment(uint _idx, address _addr) public view returns (bool, string memory, uint, uint){
        bool  _exist =   userInfo[ _addr].orders1[_idx].comment.exist;
        string storage _content = userInfo[ _addr].orders1[_idx].comment.content;

        uint _score = userInfo[ _addr].orders1[_idx].comment.score;
        uint  _commentTime = userInfo[ _addr].orders1[_idx].comment.time;
        return (_exist, _content, _score, _commentTime);
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
        uint _time =  userInfo[ _addr].orders1[_idx].time;
        string storage _OTA = userInfo[ _addr].orders1[_idx].OTA;
        string storage _state = userInfo[ _addr].orders1[_idx].state;
        return (_time, _OTA, _state);
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

}