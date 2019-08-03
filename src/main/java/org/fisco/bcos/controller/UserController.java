package org.fisco.bcos.controller;

import org.fisco.bcos.exception.TransactionExecException;
import org.fisco.bcos.model.CommonResult;
import org.fisco.bcos.model.TrustTravel;
import org.fisco.bcos.model.User;
import org.fisco.bcos.service.UserService;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;

@RestController
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;



    /**
     * @api {POST} /user register a user
     * @apiName AddUser
     * @apiGroup User
     *
     * @apiParamExample {json} Request-Example:
     *     {
     *       "username": "john",
     *       "password": "123456"
     *     }
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "message": "success",
     *     }
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "message": "user existed",
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
    @PostMapping("/user")
    public ResponseEntity<CommonResult> registerUser(@RequestBody User user) {
        CommonResult commonResult = userService.registerUser(user);
        switch (commonResult.getMessage()) {
            case "success" :
                return new ResponseEntity<>(commonResult, HttpStatus.OK);
            case "user existed" :
                return new ResponseEntity<>(commonResult, HttpStatus.OK);
            case "user not exist":
                return new ResponseEntity<>(commonResult, HttpStatus.BAD_REQUEST);
            case "server error":
                return new ResponseEntity<>(commonResult, HttpStatus.INTERNAL_SERVER_ERROR);
            default:
                System.exit(1); // this should nerve happen;
        }
        return null;    // cheat the compiler
    }

    /**
     * @api {GET} /user get user address by username
     * @apiName getUserAddress
     * @apiGroup User
     *
     * @apiParamExample {json} Request-Example:
     *     {
     *       "username": "john"
     *     }
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "message": "success",
     *       "addr": "0x....."
     *     }
     *
     * @apiErrorExample Client-Error-Response:
     *     HTTP/1.1 400 Bad Request
     *     {
     *        "message": "user not exist"
     *     }
     *
     * @apiErrorExample Server-Error-Response:
     *     HTTP/1.1 500 Server Error
     *     {
     *       "message": "server error"
     *     }
     */
    @GetMapping("/user/{username}")
    public ResponseEntity<CommonResult<String>> getUserAddress(@PathVariable String username) {
        CommonResult<String> commonResult = userService.getUserAddress(username);
        switch (commonResult.getMessage()) {
            case "success" :
                return new ResponseEntity<>(commonResult, HttpStatus.OK);
            case "user not exist":
                return new ResponseEntity<>(commonResult, HttpStatus.BAD_REQUEST);
            case "server error":
                return new ResponseEntity<>(commonResult, HttpStatus.INTERNAL_SERVER_ERROR);
            default:
                System.exit(1); // this should nerve happen;
        }
        return null;    // cheat the compiler
    }

    /**
     * @api {POST} /user user login
     * @apiName  user login
     * @apiGroup User
     *
     * @apiParamExample {json} Request-Example:
     *     {
     *       "username": "john",
     *       "password": "123456"
     *     }
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "message": "success"
     *       "addr": "0x..."
     *     }
     *
     * @apiErrorExample Client-Error-Response:
     *     HTTP/1.1 400 Bad Request
     *     {
     *         "message": "username or password error"
     *     }
     *
     * @apiErrorExample Server-Error-Response:
     *     HTTP/1.1 500 Server Error
     *     {
     *       "message": "server error"
     *     }
     */
    @PostMapping("/user/login")
    public ResponseEntity<CommonResult<String>> userLogin(@RequestBody User user) {
        CommonResult<String> commonResult = userService.userLogin(user);
        switch (commonResult.getMessage()) {
            case "success" :
                return new ResponseEntity<>(commonResult, HttpStatus.OK);
            case "username or password error":
                return new ResponseEntity<>(commonResult, HttpStatus.BAD_REQUEST);
            case "server error":
                return new ResponseEntity<>(commonResult, HttpStatus.INTERNAL_SERVER_ERROR);
            default:
                System.exit(1); // this should nerve happen;
        }
        return null;    // cheat the compiler
    }

}
