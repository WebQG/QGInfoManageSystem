package com.qg.www.controllers;

import com.qg.www.dtos.RequestData;
import com.qg.www.dtos.ResponseData;
import com.qg.www.service.UserService;
import com.qg.www.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author net
 * @version 1.0
 * 用户业务控制器，控制登录、注册等功能。
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Resource
    private UserServiceImpl userService;
    /**
     *用户注册；
     * @param data 用户注册的基本信息；
     * @return 状态码；
     */
    @PostMapping("/register")
    public ResponseData userRegister(@RequestBody RequestData data){
        System.out.println("收到请求");
        return userService.userRegister(data);
    }
}
