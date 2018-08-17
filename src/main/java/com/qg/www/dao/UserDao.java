package com.qg.www.dao;


import com.qg.www.dtos.RequestData;
import com.qg.www.models.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author net
 * @version 1.0
 * 用户dao接口；
 */
public interface UserDao {

    /**
     * 判断用户名是否存在
     * @param userName 用户名
     * @return 大于0：存在 0：不存在
     */
    User isExist(@Param("userName") String userName);

    /**
     * 向数据库插入用户信息
     * @param requestData 用户名、用户密码、用户真实姓名
     * @return 1：插入成功 0：插入失败
     */
    int register(@Param("data") RequestData requestData);

    /**
     * 查询用户名和密码是否存在
     * @param requestData 用户名、用户密码
     * @return 1：存在 0：不存在
     */
    User login(RequestData requestData);

    /**
     * 审核用户注册
     * @param userName 用户名
     * @param passOrNot 是否通过注册 1：通过 0：不通过
     * @return 1：更改成功 0：更改失败
     */
    int review(@Param("userName") String userName, @Param("passOrNot") int passOrNot);

    /**
     * 通过用户的ID获取用户的权限；
     * @param userId
     * @return
     */
    int getPrivilegeById(@Param("userId") int userId);

    /**
     * 撤销用户的注册
     * @param userName 用户名；
     * @return 撤销的用户数；
     */
    int deleteUserByUserName(@Param("userName") String userName);

}
