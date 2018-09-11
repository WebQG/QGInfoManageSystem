package com.qg.www.controllers;

import com.qg.www.dtos.RequestData;
import com.qg.www.dtos.ResponseData;
import com.qg.www.enums.Status;
import com.qg.www.service.UserService;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author net
 * @version 1.0
 * 用户业务控制器，控制登录、注册等功能。
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 用户注册；
     *
     * @param data 用户注册的基本信息；
     * @return 状态码；
     */
    @PostMapping("/register")
    public ResponseData userRegister(@RequestBody RequestData data) {
        return userService.userRegister(data);
    }

    /**
     * 用户登录
     *
     * @param data 用户登录的用户名和密码
     * @return 用户真实名字、用户权限、状态码
     */
    @PostMapping("/login")
    public ResponseData userLogin(@RequestBody RequestData data, HttpServletRequest request) {
        ResponseData responseData = userService.userLogin(data);
        //设置登录状态
        HttpSession session = request.getSession();
        session.setAttribute("privilege", responseData.getPrivilege());
        session.setAttribute("name", responseData.getName());
        System.out.println("正在登录，权限是：" + request.getSession().getAttribute("privilege"));
        return responseData;
    }

    /**
     * 用户账号审核工具；
     *
     * @param data 处理的用户名和是否激活用户的标识
     * @return 状态码
     */
    @PostMapping("/review")
    public ResponseData userReview(@RequestBody RequestData data, HttpServletRequest request) {
        Integer privilege = (Integer) request.getSession().getAttribute("privilege");
        return userService.userReview(data, privilege);
    }

    /**
     * 获取未激活用户列表
     *
     * @param data 登录者的用户名
     * @return 状态码和未激活用户列表
     */
    @PostMapping("/listuser")
    public ResponseData getUnactivedUsers(@RequestBody RequestData data) {
        return userService.getUnavtivedUsers(data);
    }

    /**
     * 获取黑名单用户
     *
     * @param data 用户名
     * @return 状态码和黑名单用户列表
     */
    @PostMapping("/listnotactive")
    public ResponseData getBlackList(@RequestBody RequestData data) {
        return userService.getBlackList(data);
    }

    @PostMapping("queryinfo")
    public ResponseData queryInfo(@RequestBody RequestData data) {
        return userService.getInfoByName(data);
    }

    /**
     * 退出登录；
     *
     * @param request 前端请求；
     */
    @PostMapping("/quit")
    public ResponseData logOut(HttpServletRequest request) {
        request.getSession().invalidate();
        ResponseData responseData = new ResponseData();
        responseData.setStatus(Status.NORMAL.getStatus());
        return responseData;
    }

    @PostMapping("/getinfo")
    public ResponseData getInfo(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        ResponseData responseData = new ResponseData();
        responseData.setStatus(Status.NORMAL.getStatus());
        responseData.setName((String) session.getAttribute("name"));
        if (responseData.getName() == null) {
            try {
                response.sendRedirect("login.html");
            } catch (IOException e) {
                responseData.setStatus(Status.SERVER_HAPPEN_ERROR.getStatus());
            }
        }
        responseData.setPrivilege((Integer) session.getAttribute("privilege"));
        return responseData;
    }

    /**
     * 下载模板文件
     *
     * @param type    奖项信息模板或者成员信息模板
     * @param request 请求
     * @return 模板文件
     */
    @GetMapping("/download")
    public ResponseEntity<byte[]> downLoadModeFile(@Param("type") String type, HttpServletRequest request) throws IOException {
        String fileName = null;
        File file;
        if (type == null || "".equals(type)) {
            //成员信息模板
            file = new File(request.getServletContext().getRealPath("") +"奖项信息.xls");
        }else {
            //奖项信息模板
            file=new File(request.getServletContext().getRealPath("") +"成员信息.xls");
        }
        HttpHeaders headers = new HttpHeaders();
        //为了解决中文名称乱码问题
        try {
            fileName = new String(file.getName().getBytes("UTF-8"), "iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.OK);
    }
}
