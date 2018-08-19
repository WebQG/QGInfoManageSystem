package com.qg.www.enums;

/**
 * @author net
 * @version 1.0
 * 用户信息表头枚举常量类
 */
public enum UserInfoHead {
    /**
     * 成员姓名
     */
    USERNAME("成员姓名"),
    /**
     * 成员组别
     */
    USER_GROUP("成员组别"),
    /**
     * 所属学院
     */
    COLLEGE("所属学院"),
    /**
     * 学号
     */
    STUDENT_NUMBER("学号"),
    /**
     * 联系电话
     */
    TELEPHONE("联系电话"),
    /**
     * 籍贯
     */
    BIRTHPLACE("籍贯"),
    /**
     * QQ账号
     */
    QQ("QQ账号"),
    /**
     * 常用邮箱
     */
    EMAIL("常用邮箱"),
    /**
     * 个人简介
     */
    DESCRIPTION("简介")
    ;
    private String userHead;
    UserInfoHead(String head){
        this.userHead=head;
    }
    public String getUserInfoHead(){
        return this.userHead;
    }
}
