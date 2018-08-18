package com.qg.www.service;

import com.qg.www.dtos.RequestData;
import com.qg.www.dtos.ResponseData;

/**
 * @author net
 * @version 1.0
 * 用户业务接口；
 */
public interface UserService {
    /**
     * 用户注册业务接口；
     *
     * @param data 用户注册的基本信息；
     * @return 状态码；
     */
    ResponseData userRegister(RequestData data);

    /**
     * 用户登录业务接口
     *
     * @param data 用户登录的账号密码
     * @return 状态码和登录后的部分用户信息；
     */
    ResponseData userLogin(RequestData data);

    /**
     * 账户审核
     *
     * @param data 处理的用户名和是否激活用户的标识
     * @return 状态码
     */
    ResponseData userReview(RequestData data);

    /**
     * 获取未激活用户列表
     *
     * @param data 请求者的用户名
     * @return 未激活用户列表和状态码
     */
    ResponseData getUnavtivedUsers(RequestData data);
}
