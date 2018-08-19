package com.qg.www.dao;

import com.qg.www.models.UserInfo;

import java.util.List;

/**
 * @author net
 * @version 1.0
 * 成员信息dao接口
 */
public interface UserInfoDao {
    /**
     * 查询所有用户信息；
     *
     * @return 用户信息列表
     */
    List<UserInfo> queryUserInfo();

    /**
     * 添加成员信息
     *
     * @param userInfo 成员信息
     * @return 添加成功的数
     */
    int addUserInfo(UserInfo userInfo);

}
