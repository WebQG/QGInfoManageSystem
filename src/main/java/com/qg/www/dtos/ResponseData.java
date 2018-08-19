package com.qg.www.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.qg.www.models.AwardInfo;
import com.qg.www.models.User;
import com.qg.www.models.UserInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author net
 * @version 1.0
 * 数据传输对象类；
 */
@Getter
@Setter
@Service
@Scope("prototype")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData {
    /**
     * 响应状态码
     */
    private String status;
    /**
     * 用户的权限
     */
    private Integer privilege;
    /**
     * 用户的名字
     */
    private String name;
    /**
     * 用户列表；
     */
    private List<User> userList;
    /**
     * 奖项列表
     */
    private List<AwardInfo> awardInfoList;
    /**
     * 成员信息列表
     */
    private List<UserInfo> userInfoList;
}
