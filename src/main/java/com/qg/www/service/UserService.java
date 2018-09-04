package com.qg.www.service;

import com.qg.www.dtos.RequestData;
import com.qg.www.dtos.ResponseData;
import com.qg.www.models.UserInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
     * @param previlege 权限值
     * @return 状态码
     */
    ResponseData userReview(RequestData data,Integer previlege);

    /**
     * 获取未激活用户列表
     *
     * @param data 请求者的用户名
     * @return 未激活用户列表和状态码
     */
    ResponseData getUnavtivedUsers(RequestData data);

    /**
     * 根据名称查找信息
     * @param data 名称
     * @return 编号、名字、组别、年级、图片地址 或  编号、名称、时间、参赛学生、图片地址
     */
    ResponseData getInfoByName(RequestData data);

    /**
     * 获取黑名单列表
     * @param data 请求数据
     * @return 返回黑名单列表还有状态码；
     */
    ResponseData getBlackList(RequestData data);

}
