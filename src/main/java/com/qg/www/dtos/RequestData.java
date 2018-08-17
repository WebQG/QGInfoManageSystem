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

}
