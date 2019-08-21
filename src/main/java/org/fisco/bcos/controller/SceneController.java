package org.fisco.bcos.controller;

import org.fisco.bcos.model.CommentResult;
import org.fisco.bcos.model.CommonResult;
import org.fisco.bcos.model.HotelOrder;
import org.fisco.bcos.model.SceneOrder;

import org.fisco.bcos.service.SceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SceneController {
    @Autowired
    private SceneService sceneService;

    /**
     * @api {post} /scene 订购旅游门票
     * @apiName bookScene
     * @apiGroup Scene
     *
     * @apiParamExample {json} Request-Example:
     *     {
     *       "addr": "0x...",
     *       "province": "安徽",
     *       "city": "黄山市",
     *       "name": "黄山",
     *       "price": 200,
     *       "ota": "携程",
     *       "flag": 1
     *     }
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "message": "success",
     *       "data": {
     *          "txhash": "0x.."
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
    @PostMapping("/scene")
    public ResponseEntity<CommonResult> bookScene(@RequestBody SceneOrder sceneOrder) {
        CommonResult commonResult = sceneService.bookScene(sceneOrder);
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
     * @api {get} /scene/count/:addr 获取用户旅游订单数量
     * @apiParam {String} user address
     *
     * @apiName getSceneOrderCount
     * @apiGroup Scene
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
    @GetMapping("/scene/count/{addr}")
    public ResponseEntity<CommonResult> getSceneOrderCount(@PathVariable String addr) {
        CommonResult commonResult = sceneService.getSceneOrderCount(addr);
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
     * @api {get} /scene/:addr/:index 获取用户旅游订单详细信息
     * @apiParam {String} addr user address
     * @apiParam {Number} index 用户订单索引
     *
     * @apiName getSceneOrderDetail
     * @apiGroup Scene
     *
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "message": "success",
     *       "data": {
     *          "province": "安徽",
     *          "city": "黄山市",
     *          "name": "黄山",
     *          "price": 200,
     *          "ota": "携程",
     *          "flag": 1
     *          "OTA": "携程"，
     *          "state": "Initialization",
     *          "time": "2312412" (unix时间戳)
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
    @GetMapping("/scene/{addr}/{index}")
    public ResponseEntity<CommonResult> getSceneOrderDetail(@PathVariable String addr, @PathVariable int index) {
        CommonResult commonResult = sceneService.getSceneOrderDetail(addr, index);
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
     * @api {post} /scene/comment 评论旅游服务
     *
     * @apiName commentHotel
     * @apiGroup Scene
     *
     * @apiParamExample {json} Request-Example:
     *     {
     *       "addr": "0x...",
     *       "index": 1,
     *       "content": "很好玩 景色很美",
     *       "score": 4 (1-5)
     *     }
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "message": "success",
     *       "data": {
     *          "txhash": "0x.."
     *       }
     *     }
     *
     *     HTTP/1.1 200 OK
     *     {
     *        "message": "the user not order",  // 用户未订购过该景点
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
    @PostMapping("/scene/comment")
    public ResponseEntity<CommonResult> commentScene(@RequestBody CommentResult commentResult) {
        CommonResult commonResult = sceneService.commentScene(commentResult.getAddr(),commentResult.getIndex(),commentResult.getContent(), commentResult.getScore());
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
     * @api {get} /scene/comment/:addr/:index 获取用户旅游服务评论
     * @apiParam {String} addr user address
     * @apiParam {Number} index 用户订单索引
     *
     * @apiName getSceneOrderComment
     * @apiGroup Scene
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
    @GetMapping("/scene/comment/{addr}/{index}")
    public ResponseEntity<CommonResult> getSceneOrderComment(@PathVariable String addr, @PathVariable int index) {
        CommonResult commonResult = sceneService.getSceneOrderComment(addr, index);
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
