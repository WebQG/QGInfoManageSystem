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
}
