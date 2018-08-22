package com.qg.www.controllers;

import com.qg.www.dtos.RequestData;
import com.qg.www.dtos.ResponseData;
import com.qg.www.models.User;
import com.qg.www.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    private UserService userService;
    /**
     * 用户注册；
     *
     * @param data 用户注册的基本信息；
     * @return 状态码；
     */
    @PostMapping("/register")
    public ResponseData userRegister(@RequestBody RequestData data) {
        return userService.userRegister(data);
    }

    /**
     * 用户登录
     *
     * @param data 用户登录的用户名和密码
     * @return 用户真实名字、用户权限、状态码
     */
    @PostMapping("/login")
    public ResponseData userLogin(@RequestBody RequestData data,HttpServletRequest request) {
        ResponseData responseData=userService.userLogin(data);
        //设置登录状态
        request.getSession().setAttribute("privilege",responseData.getPrivilege());
        System.out.println(request.getSession().getAttribute("privilege"));
        return responseData;
    }

    /**
     * 用户账号审核工具；
     *
     * @param data 处理的用户名和是否激活用户的标识
     * @return 状态码
     */
    @PostMapping("/review")
    public ResponseData userReview(@RequestBody RequestData data,HttpServletRequest request) {
        Integer privilege=(Integer) request.getSession().getAttribute("privilege");
        return userService.userReview(data,privilege);
    }

    /**
     * 获取未激活用户列表
     *
     * @param data 登录者的用户名
     * @return 状态码和未激活用户列表
     */
    @PostMapping("/listuser")
    public ResponseData getUnactivedUsers(@RequestBody RequestData data) {
        return userService.getUnavtivedUsers(data);
    }


    @PostMapping("queryinfo")
    public ResponseData queryInfo(@RequestBody RequestData data) {
        return userService.getInfoByName(data);
    }

    /**
     * 测试代码；
     * @param request
     */
    @PostMapping("test")
    public void loginTest(HttpServletRequest request){
        request.getSession().setAttribute("user","linxu");
        System.out.println("我已经设置了session");
    }
}
