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
    CANCEL_REGISTER(0),
    /**
     *未注册的
     */
    NOT_ACTIVE(0)
    ;
    private Integer code;
    UserOperate(Integer code){
        this.code=code;
    }
    public Integer getUserOperateCode(){
        return this.code;
    }
}
