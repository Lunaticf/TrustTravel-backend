package org.fisco.bcos.controller;

import com.alibaba.fastjson.JSONObject;
import org.fisco.bcos.model.CommentResult;
import org.fisco.bcos.model.CommonResult;
import org.fisco.bcos.model.HotelOrder;
import org.fisco.bcos.model.User;
import org.fisco.bcos.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HotelController {

    @Autowired
    private HotelService hotelService;

    /**
     * @api {post} /hotel 订购房间服务
     * @apiName bookHotel
     * @apiGroup Hotel
     *
     * @apiParamExample {json} Request-Example:
     *     {
     *       "detailAddr": "朝阳区.....",
     *       "addr": "0x...",
     *       "hotel": "如家",
     *       "roomType": "大床房",
     *       "fromDate": "2019-8-1", (前端可自行设置时间格式)
     *       "toDate": "2019-8-3", (前端可自行设置时间格式)
     *       "ota": "携程"，
     *       "totalPrice": 500,
     *       "flag": 1
     *     }
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "message": "success",
     *       "data": {
     *           "txhash": "0x.."
     *       }
     *     }
     *
     * @apiErrorExample Client-Error-Response:
     *     HTTP/1.1 400 Bad Request
     *
     * @apiErrorExample Server-Error-Response:
     *     HTTP/1.1 500 Server Error
     *     {
     *       "message": "server error/order failed"
     *     }
     */
    @PostMapping("/hotel")
    public ResponseEntity<CommonResult> bookHotel(@RequestBody HotelOrder hotelOrder) {
        System.out.println("***" + hotelOrder);
        CommonResult commonResult = hotelService.bookHotel(hotelOrder);
        switch (commonResult.getMessage()) {
            case "success" :
                return new ResponseEntity<>(commonResult, HttpStatus.OK);
            case "order failed":
                return new ResponseEntity<>(commonResult, HttpStatus.INTERNAL_SERVER_ERROR);
            case "server error":
                return new ResponseEntity<>(commonResult, HttpStatus.INTERNAL_SERVER_ERROR);
            default:
                System.exit(1); // this should nerve happen;
        }
        return null;    // cheat the compiler
    }

    /**
     * @api {get} /hotel/count/:addr 获取用户酒店订单数量
     * @apiParam {String} user address
     *
     * @apiName getHotelOrderCount
     * @apiGroup Hotel
     *
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "message": "success",
     *       "data": {
     *           "count": 3
     *       }
     *     }
     *
     * @apiErrorExample Client-Error-Response:
     *     HTTP/1.1 400 Bad Request
     *
     * @apiErrorExample Server-Error-Response:
     *     HTTP/1.1 500 Server Error
     *     {
     *       "message": "server error"
     *     }
     */
    @GetMapping("/hotel/count/{addr}")
    public ResponseEntity<CommonResult> getHotelOrderCount(@PathVariable String addr) {
        CommonResult commonResult = hotelService.getHotelOrderCount(addr);
        switch (commonResult.getMessage()) {
            case "success" :
                return new ResponseEntity<>(commonResult, HttpStatus.OK);
            case "server error":
                return new ResponseEntity<>(commonResult, HttpStatus.INTERNAL_SERVER_ERROR);
            default:
                System.exit(1); // this should nerve happen;
        }
        return null;    // cheat the compiler
    }

    /**
     * @api {get} /hotel/:addr/:index 获取用户酒店订单详细信息
     * @apiParam {String} addr user address
     * @apiParam {Number} index 用户订单索引
     *
     * @apiName getHotelOrderDetail
     * @apiGroup Hotel
     *
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "message": "success",
     *       "data": {
     *            "hotel": "如家",
     *            "roomType": "大床房",
     *            "fromDate": "2019-8-1",
     *            "toDate": "2019-8-3",
     *            "OTA": "携程"，
     *            "totalPrice": 500,
     *            "state": "Initialization",
     *            "time": "2312412" (unix时间戳),
     *            "hash": "0x..."
     *       }
     *     }
     *
     * @apiErrorExample Client-Error-Response:
     *     HTTP/1.1 400 Bad Request
     *
     * @apiErrorExample Server-Error-Response:
     *     HTTP/1.1 500 Server Error
     *     {
     *       "message": "server error"
     *     }
     */
    @GetMapping("/hotel/{addr}/{index}")
    public ResponseEntity<CommonResult> getHotelOrderDetail(@PathVariable String addr, @PathVariable int index) {
        CommonResult commonResult = hotelService.getHotelOrderDetail(addr, index);
        switch (commonResult.getMessage()) {
            case "success" :
                return new ResponseEntity<>(commonResult, HttpStatus.OK);
            case "server error":
                return new ResponseEntity<>(commonResult, HttpStatus.INTERNAL_SERVER_ERROR);
            default:
                System.exit(1); // this should nerve happen;
        }
        return null;    // cheat the compiler
    }


    /**
     * @api {post} /hotel/comment 评论酒店服务
     *
     * @apiName commentHotel
     * @apiGroup Hotel
     *
     * @apiParamExample {json} Request-Example:
     *     {
     *       "addr": "0x...",
     *       "index": 1,
     *       "content": "服务很好 住着很舒服",
     *       "score": 4 (1-5)
     *     }
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "message": "success",
     *       "data": {
     *          "txhash": "0x.."
     *        }
     *     }
     *
     *     HTTP/1.1 200 OK
     *     {
     *        "message": "the user not order",  // 用户未订购过该房间
     *     }
     *
     * @apiErrorExample Client-Error-Response:
     *     HTTP/1.1 400 Bad Request
     *
     * @apiErrorExample Server-Error-Response:
     *     HTTP/1.1 500 Server Error
     *     {
     *       "message": "server error"
     *     }
     */
    @PostMapping("/hotel/comment")
    public ResponseEntity<CommonResult> commentHotel(@RequestBody CommentResult commentResult) {
        CommonResult commonResult = hotelService.commentHotel(commentResult.getAddr(),commentResult.getIndex(),commentResult.getContent(), commentResult.getScore());
        switch (commonResult.getMessage()) {
            case "success" :
                return new ResponseEntity<>(commonResult, HttpStatus.OK);
            case "user not order":
                return new ResponseEntity<>(commonResult, HttpStatus.OK);
            case "server error":
                return new ResponseEntity<>(commonResult, HttpStatus.INTERNAL_SERVER_ERROR);
            default:
                System.exit(1); // this should nerve happen;
        }
        return null;    // cheat the compiler
    }

    /**
     * @api {get} /hotel/comment/:addr/:index 获取用户酒店订单评论
     * @apiParam {String} addr user address
     * @apiParam {Number} index 用户订单索引
     *
     * @apiName getHotelOrderComment
     * @apiGroup Hotel
     *
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "message": "success",
     *       "data": {
     *            "exist": true/false, !(表示是否评论了这个订单 false就不用解析了)
     *            "content": "很好",
     *            "time": "2312412" (unix时间戳)
     *            "score": 5 (1-5),
     *            "hash": "0x..."
     *       }
     *     }
     *
     * @apiErrorExample Client-Error-Response:
     *     HTTP/1.1 400 Bad Request
     *
     * @apiErrorExample Server-Error-Response:
     *     HTTP/1.1 500 Server Error
     *     {
     *       "message": "server error"
     *     }
     */
    @GetMapping("/hotel/comment/{addr}/{index}")
    public ResponseEntity<CommonResult> getHotelOrderComment(@PathVariable String addr, @PathVariable int index) {
        CommonResult commonResult = hotelService.getHotelOrderComment(addr, index);
        switch (commonResult.getMessage()) {
            case "success" :
                return new ResponseEntity<>(commonResult, HttpStatus.OK);
            case "server error":
                return new ResponseEntity<>(commonResult, HttpStatus.INTERNAL_SERVER_ERROR);
            default:
                System.exit(1); // this should nerve happen;
        }
        return null;    // cheat the compiler
    }
}
