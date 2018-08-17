package com.qg.www.enums;

/**
 * @author net
 * @version 1.0
 * 状态码枚举常量类；
 */
public enum Status {
    /**
     * 一切正常；
     */
    NORMAL("1"),
    /**
     *用户名已经注册
     */
    USERNAME_ISREGISTERED("2"),
    /**
     * 账户未激活
     */
    ACCOUNT_NOT_ACTIVE("3"),
    /**
     * 账户不存在
     */
    ACCOUNT_NOT_EXIT("4"),
    /**
     * 密码错误
     */
    PASSWORD_ERROR("5"),
    /**
     * 信息导入导出失败
     */
    ;

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
