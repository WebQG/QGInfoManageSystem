package com.qg.www.enums;

/**
 * @author net
 * @version 1.0
 * 用户操作码枚举类；
 */
public enum UserOperate {
    /**
     * 允许注册；
     */
    ALLOW_REGISTER(1),
    /**
     * 撤销注册；
     */
    CANCEL_REGISTER(2),
    /**
     *未激活的
     */
    NOT_ACTIVE(0),
    /**
     * 添加进黑名单
     */
    BLACKLIST_USER(3),
    /**
     * 管理员权限
     */
    ADMIN_PRIVILEGE(2)
    ;
    private Integer code;
    UserOperate(Integer code){
        this.code=code;
    }
    public Integer getUserOperateCode(){
        return this.code;
    }
}
