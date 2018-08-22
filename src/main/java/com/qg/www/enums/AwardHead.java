package com.qg.www.enums;

/**
 * @author net
 * @version 1.0
 * 奖项头部枚举常量类
 */
public enum AwardHead {
    /**
     * 奖项的名称
     */
    AWARD_NAME("奖项名称"),
    /**
     * 获奖时间
     */
    AWARD_GET_TIME("获奖时间"),
    /**
     * 奖项级别
     */
    AWARD_LEVEL("奖项级别"),
    /**
     * 奖项等级
     */
    AWARD_RANK("奖项等级"),
    /**
     * 授奖部门
     */
    AWARD_DEPARTMENT("授奖部门"),
    /**
     * 指导老师
     */
    LEAD_TEACHER("指导老师"),
    /**
     * 参赛学生
     */
    JOIN_STUDENT("参赛学生"),
    /**
     * 奖项简介
     */
    AWARD_DESCRIPTION("奖项简介"),
    /**
     * 获奖作品
     */
    AWARD_PROJECT("获奖作品")
    ;
    private String awardHead;
    AwardHead(String head){
        this.awardHead=head;
    }
    public String getAwardHead(){
        return awardHead;
    }
}
