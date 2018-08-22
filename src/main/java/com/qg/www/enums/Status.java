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
    ACCOUNT_NOT_EXIST("4"),
    /**
     * 密码错误
     */
    PASSWORD_ERROR("5"),
    /**
     * 信息导入导出失败
     */
    INFO_SOLVE_ERROR("6"),
    /**
     * 服务器出现故障
     */
    SERVER_HAPPEN_ERROR("7"),
    /**
     * 信息修改失败
     */
    INFO_MODIFY_ERROR("8"),
    /**
     * 数据格式错误
     */
    DATA_FORMAT_ERROR("9"),
    /**
     * 信息不存在
     */
     INFO_LACK("10"),
    /**
     * 没权限
     */
    NO_PROVILEGE("11")
    ;

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
