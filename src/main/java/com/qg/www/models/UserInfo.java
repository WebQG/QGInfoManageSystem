package com.qg.www.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * @author net
 * @version 1.0
 * 用户信息类；
 */
@Service
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo {
    /**
     * 用户信息编号
     */
    private Integer userInfoId;
    /**
     * 成员名字
     */
    private String name;
    /**
     * 组别
     */
    private String group;
    /**
     * 学院
     */
    private String college;
    /**
     * 学号
     */
    private String studentNum;
    /**
     * 电话号码
     */
    private String tel;
    /**
     * 籍贯
     */
    private String birthplace;
    /**
     * QQ
     */
    private String qq;
    /**
     * 邮件
     */
    private String email;
    /**
     * 简介
     */
    private String description;
    /**
     * 头像URL
     */
    private String url;
}
