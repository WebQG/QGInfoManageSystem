package com.qg.www.service.impl;

import com.qg.www.dao.UserDao;
import com.qg.www.dtos.RequestData;
import com.qg.www.dtos.ResponseData;
import com.qg.www.enums.Status;
import com.qg.www.enums.UserOperate;
import com.qg.www.models.User;
import com.qg.www.service.UserService;
import com.qg.www.utils.DigestUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

/**
 * @author net
 * @version 1.0
 * 用户业务接口实现类；
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    /**
     * 用户注册业务接口；
     *
     * @param data 用户注册的基本信息；
     * @return 状态码；
     */
    @Override
    public ResponseData userRegister(RequestData data) {
        ResponseData responseData = new ResponseData();
        //获取注册用户名
        String userName = data.getUserName();
        System.out.println(userName);
        //获取注册密码
        String password = data.getPassword();
        //获取注册用户名称
        String name = data.getName();
        //验证数据是否为空；
        if (null == userName || null == password || null == name) {
            //前端传送的数据出现错误；
            responseData.setStatus(Status.DATA_FORMAT_ERROR.getStatus());
        } else {
            //定义判断变量
            User user = userDao.isExist(userName);
            //如果用户名已经存在
            if (null != user) {
                responseData.setStatus(Status.USERNAME_ISREGISTERED.getStatus());
            } else {
                data.setPassword(DigestUtil.digestPassword(password));
                userDao.register(data);
                responseData.setStatus(Status.NORMAL.getStatus());
            }
        }
        return responseData;
    }

    /**
     * 用户登录业务接口
     *
     * @param data 用户登录的账号密码
     * @return 状态码和登录后的部分用户信息；
     */
    @Override
    public ResponseData userLogin(RequestData data) {
        ResponseData responseData = new ResponseData();
        //获取登录的用户名；
        String userName = data.getUserName();
        //获取登录的密码；
        String password = data.getPassword();
        if (null == userName || null == password) {
            responseData.setStatus(Status.DATA_FORMAT_ERROR.getStatus());
        } else {
            User user = userDao.isExist(userName);
            //判断用户名是否存在；
            if (null == user) {
                //用户名不存在
                responseData.setStatus(Status.ACCOUNT_NOT_EXIST.getStatus());
            } else {
                data.setPassword(DigestUtil.digestPassword(password));
                User loginUser = userDao.login(data);
                //登录失败,密码错误；
                if (null == loginUser) {
                    responseData.setStatus(Status.PASSWORD_ERROR.getStatus());
                } else {
                    //获取登录账户，判断是否是已经激活状态；
                    if (loginUser.getStatus() == 1) {
                        //登录成功；
                        responseData.setStatus(Status.NORMAL.getStatus());
                        responseData.setName(loginUser.getName());
                        responseData.setPrivilege(loginUser.getPrivilege());
                    } else {
                        responseData.setStatus(Status.ACCOUNT_NOT_ACTIVE.getStatus());
                    }
                }
            }
        }
        return responseData;
    }

    /**
     * 账户审核
     *
     * @param data 处理的用户名和是否激活用户的标识
     * @return 状态码
     */
    @Override
    public ResponseData userReview(RequestData data) {
        ResponseData responseData = new ResponseData();
        int solveNum = 0;
        //定义用户名变量
        String userName;
        //获取UserList;
        List<User> userList = data.getUserList();
        //获取操作码，0表示撤销用户注册，1表示激活用户；
        Integer passOrNot = data.getPassOrNot();
        if (!userList.isEmpty()) {
            //如果用户列表不为空
            Iterator<User> iterator = userList.iterator();
            //如果批准用户；
            if (passOrNot.equals(UserOperate.ALLOW_REGISTER.getUserOperateCode())) {
                //创建迭代，进行遍历修改用户；
                while (iterator.hasNext()) {
                    userName = iterator.next().getUserName();
                    solveNum = userDao.review(userName, passOrNot);
                }
                //判断是否处理失败
                if (solveNum == 0) {
                    responseData.setStatus(Status.SERVER_HAPPEN_ERROR.getStatus());
                    return responseData;
                }
            } else {
                //对用户进行遍历撤销；
                while (iterator.hasNext()) {
                    userName = iterator.next().getUserName();
                    solveNum = userDao.deleteUserByUserName(userName);
                }
                //判断是否撤销失败
                if (solveNum == 0) {
                    responseData.setStatus(Status.SERVER_HAPPEN_ERROR.getStatus());
                    return responseData;
                }
            }
            responseData.setStatus(Status.NORMAL.getStatus());
        } else {
            //前端数据出现错误；
            responseData.setStatus(Status.DATA_FORMAT_ERROR.getStatus());
        }
        return responseData;
    }

    /**
     * 获取未激活用户列表
     *
     * @param data 请求者的用户名
     * @return 未激活用户列表和状态码
     */
    @Override
    public ResponseData getUnavtivedUsers(RequestData data) {
        ResponseData responseData = new ResponseData();
        //定义用户名变量
        String userName = data.getUserName();
        if (null == userName) {
            responseData.setStatus(Status.DATA_FORMAT_ERROR.getStatus());
        } else {
            List<User> userList = userDao.queryUserByStatus(UserOperate.NOT_ACTIVE.getUserOperateCode());
            responseData.setStatus(Status.NORMAL.getStatus());
            responseData.setUserList(userList);
        }
        return responseData;
    }
}
