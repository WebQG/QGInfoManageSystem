package com.qg.www.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * @author net
 * @version 1.0
 * 用户类；
 */
@Service
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户权限，1为普通成员，2为管理员
     */
    private Integer privilege;
    /**
     * 用户状态，0为未激活，1为已经激活
     */
    private Integer status;
    /**
     * 用户真实名字
     */
    private String name;
}
