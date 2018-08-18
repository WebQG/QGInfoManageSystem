package com.qg.www.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * @author net
 * @version 1.0
 * 奖项类；
 */
@Setter
@Getter
@Service("awardInfo")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AwardInfo {
    /**
     * 奖项编号
     */
    private Integer awardId;
    /**
     * 奖项名称
     */
    private String awardName;
    /**
     * 获奖时间
     */
    private String awardTime;
    /**
     * 奖项级别
     */
    private String awardLevel;
    /**
     * 奖项等级
     */
    private String rank;
    /**
     * 授奖部门
     */
    private String department;
    /**
     * 指导老师
     */
    private String leadTeacher;
    /**
     * 参赛学生
     */
    private String joinStudent;
    /**
     * 奖项简介
     */
    private String awardDescription;
    /**
     * 奖项图片的URL
     */
    private String url;
}
