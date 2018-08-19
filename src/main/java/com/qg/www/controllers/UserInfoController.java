package com.qg.www.controllers;

import com.qg.www.service.UserInfoService;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
}
