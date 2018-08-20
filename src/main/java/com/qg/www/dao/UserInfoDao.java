package com.qg.www.dao;

import com.qg.www.dtos.RequestData;
import com.qg.www.models.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.apache.xmlbeans.impl.xb.xsdschema.impl.AttributeImpl;


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

    /**
     * 根据关键字查询成员信息
     *
     * @param data      所属组别、所属年级
     * @param rowBounds 分页信息
     * @return 编号、名字、组别、年级、图片地址
     */
    List<UserInfo> queryAppointedUserInfo(RequestData data, RowBounds rowBounds);

    /**
     * 根据ID查找成员信息
     *
     * @param data 成员ID
     * @return 成员详细信息
     */
    UserInfo getUserInfoById(RequestData data);

    /**
     * 根据名称查找成员信息
     *
     * @param data 成员名称
     * @param rowBounds 分页信息
     * @return 编号、名字、组别、年级、图片地址
     */
    List<UserInfo> queryUserInfoByName(RequestData data, RowBounds rowBounds);

    /**
     * 添加成员照片；
     *
     * @param userInfoId  成员ID
     * @param pictureName 照片名字
     * @return 成功数
     */
    int addUserInfoPicture(@Param("userInfoId") Integer userInfoId, @Param("pictureName") String pictureName);

    /**
     * 提供给安卓的成员信息搜索接口
     *
     * @param data key 1为模糊搜索 2为精确搜索 、所属组别、所属年级
     * @return 编号、名字、组别、年级、图片地址
     */
    List<UserInfo> queryAppointedUserInfo(RequestData data);

    /**
     * 根据名称查找成员信息
     *
     * @param data 成员名称
     * @return 编号、名字、组别、年级、图片地址
     */
    List<UserInfo> queryUserInfoByName(RequestData data);
}
