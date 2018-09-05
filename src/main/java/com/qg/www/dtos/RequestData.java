package com.qg.www.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.qg.www.models.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author net
 * @version 1.0
 * 接收前端数据传输对象类；
 */
@Setter
@Getter
@Service
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestData {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户真实名字
     */
    private String name;
    /**
     * 批准或者撤销注册请求,0表示撤销请求，1表示激活用户
     */
    private Integer passOrNot;
    /**
     * 用户列表；
     */
    private List<User> userList;
    /**
     * 分页、第几页
     */
    private Integer page;
    /**
     * 获奖编号
     */
    private Integer awardId;
    /**
     * 获奖年份
     */
    private String awardTime;
    /**
     * 奖项级别
     */
    private String awardLevel;
    /**
     * 获奖级别
     */
    private String rank;
    /**
     * 成员编号
     */
    private Integer userInfoId;
    /**
     * 成员组别
     */
    private String group;
    /**
     * 成员年级
     */
    private String grade;
    /**
     * 奖项名称
     */
    private String awardName;
    /**
     * 获奖项目
     */
    private String awardProject;
    /**
     * 颁奖部门
     */
    private String department;
    /**
     * 指导老师
     */
    private String leadTeacher;
    /**
     * 参赛成员
     */
    private String joinStudent;
    /**
     * 奖项简介
     */
    private String awardDescription;
    /**
     * 成员学院
     */
    private String college;
    /**
     * 成员电话
     */
    private String tel;
    /**
     * 成员生日
     */
    private String birthplace;
    /**
     * qq号码
     */
    private String qq;
    /**
     * 成员邮箱
     */
    private String email;
    /**
     * 成员简述
     */
    private String description;
}
