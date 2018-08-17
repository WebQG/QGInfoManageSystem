package com.qg.www.service.impl;

import com.qg.www.dao.UserDao;
import com.qg.www.dtos.RequestData;
import com.qg.www.dtos.ResponseData;
import com.qg.www.enums.Status;
import com.qg.www.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author net
 * @version 1.0
 * 用户业务接口实现类；
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private ResponseData responseData;
    /**
     * 用户注册业务接口；
     *
     * @param data 用户注册的基本信息；
     * @return 状态码；
     */
    @Override
    public ResponseData userRegister(RequestData data) {
        //获取注册用户名
        String userName=data.getUserName();
        System.out.println(userName);
        //获取注册密码
        String password=data.getPassword();
        //获取注册用户名称
        String name=data.getName();
        //验证数据是否为空；
        if (null==userName||null==password||null==name){
            //前端传送的数据出现错误；
            responseData.setStatus(Status.DATA_FORMAT_ERROR.getStatus());
        }else {
            //定义判断变量
            int isExist=userDao.isExist(userName);
            //如果用户名已经存在
            if (isExist>0){
                responseData.setStatus(Status.USERNAME_ISREGISTERED.getStatus());
            }else {
                userDao.register(data);
                responseData.setStatus(Status.NORMAL.getStatus());
            }
        }
        return responseData;
    }
}
