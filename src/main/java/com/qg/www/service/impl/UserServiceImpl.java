package com.qg.www.service.impl;

import com.qg.www.dao.AwardInfoDao;
import com.qg.www.dao.UserDao;
import com.qg.www.dao.UserInfoDao;
import com.qg.www.dtos.RequestData;
import com.qg.www.dtos.ResponseData;
import com.qg.www.enums.Status;
import com.qg.www.enums.UserOperate;
import com.qg.www.models.AwardInfo;
import com.qg.www.models.User;
import com.qg.www.models.UserInfo;
import com.qg.www.service.AwardService;
import com.qg.www.service.UserService;
import com.qg.www.utils.DigestUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    @Resource
    private AwardInfoDao awardInfoDao;
    @Resource
    private UserInfoDao userInfoDao;
    /**
     * 成员信息一共有多少页
     */
    private Integer sign = 0;
    /**
     * 在奖状信息第一次出现时出现了多少条
     */
    private Integer used = 0;

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

    /**
     * 根据名称查找信息
     *
     * @param data 名称
     * @return 编号、名字、组别、年级、图片地址 或  编号、名称、时间、参赛学生、图片地址
     */
    @Override
    public ResponseData getInfoByName(RequestData data) {
        List<UserInfo> userInfoList;
        List<AwardInfo> awardInfoList;
        ResponseData responseData = new ResponseData();
        // 分页、5条分一页
        RowBounds rowBounds = new RowBounds(data.getPage() * 5, 5);
        // 得到成员信息列表
        userInfoList = userInfoDao.queryUserInfoByName(data, rowBounds);
        if (!userInfoList.isEmpty()) {
            if (userInfoList.size() > 5) {
                responseData.setStatus(Status.NORMAL.getStatus());
                responseData.setUserInfoList(userInfoList);
            } else {
                // 如果返回参数中应包含成员信息列表和奖状信息别彪
                RowBounds rowBounds1 = new RowBounds(0, 5 - userInfoList.size());
                // 得到奖状信息列表
                awardInfoList = awardInfoDao.queryAwardInfoByName(data, rowBounds1);
                responseData.setStatus(Status.NORMAL.getStatus());
                responseData.setUserInfoList(userInfoList);
                responseData.setAwardInfoList(awardInfoList);
                // 得到第一次中奖状信息列表已发送的条数
                used = 5 - userInfoList.size();
            }
            // 记录成员信息出现的最后一页
            sign = data.getPage();
        } else {
            // 对后面出现的奖状信息进行分页
            RowBounds rowBounds1 = new RowBounds((data.getPage() - sign - 1) * 5 + used, 5);
            awardInfoList = awardInfoDao.queryAwardInfoByName(data, rowBounds1);
            if(!awardInfoList.isEmpty()){
                responseData.setAwardInfoList(awardInfoList);
                responseData.setStatus(Status.NORMAL.getStatus());
            }else {
                responseData.setStatus(Status.INFO_LACK.getStatus());
            }
        }
        return responseData;
    }
}
