package com.qg.www.controllers;

import com.qg.www.dtos.RequestData;
import com.qg.www.dtos.ResponseData;
import com.qg.www.service.UserInfoService;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author net
 * @version 1.0
 * 成员信息控制器
 */
@CrossOrigin
@RestController
@RequestMapping("/userinfo")
public class UserInfoController {
    @Resource
    private UserInfoService userInfoService;

    /**
     * 导入成员信息excel文件
     *
     * @param file 文件
     * @return 状态码；
     */
    @PostMapping("/import")
    public ResponseData importUserInfo(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String path = request.getServletContext().getRealPath("/");
        return userInfoService.importExcel(file, path);
    }

    /**
     * 导出成员信息excel表格；
     *
     * @return 成员信息表格
     */
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportUserInfo() throws IOException {
        String fileName = null;
        String path = userInfoService.exportExcel();
        File file = new File(path);
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
                headers, HttpStatus.CREATED);
    }

    /**
     * 根据关键字查询成员信息
     *
     * @param data 所属组别、所属年级
     * @return 编号、名字、组别、年级、图片地址
     */
    @PostMapping("/queryuserinfo")
    public ResponseData queryUserInfo(@RequestBody RequestData data) {
        return userInfoService.queryUserInfo(data);
    }

    /**
     * 根据ID查找成员信息
     *
     * @param data 成员ID
     * @return 成员详细信息
     */
    @PostMapping("/getuserinfo")
    public ResponseData getUserInfo(@RequestBody RequestData data) {
        return userInfoService.getUserInfo(data);
    }

    /**
     * 添加成员信息头像
     *
     * @param file       图片
     * @param request    请求
     * @param userInfoId 用户信息ID
     * @return 状态码
     */
    @PostMapping("/modifypicture")
    public ResponseData addUserInfoPicture(MultipartFile file, HttpServletRequest request, @RequestParam(value = "userInfoId", required = false) String userInfoId) {
        String path=request.getServletContext().getRealPath("/");
        return userInfoService.addUserInfoPicture(file,path,userInfoId);
    }
}
