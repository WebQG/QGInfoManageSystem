package com.qg.www.controllers;

import com.qg.www.dtos.ResponseData;
import com.qg.www.service.AwardService;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author net
 * @version 1.0
 * 奖项控制器；
 */
@RequestMapping("/award")
@RestController
@CrossOrigin
public class AwardController {
    @Resource
    private AwardService service;

    @PostMapping("/import")
    public ResponseData importAwardInfo() {
        return null;
    }

    /**
     * 导出EXCEL文件
     *
     * @return
     */
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportAwardInfo() throws IOException {
        String path = service.exportExcel();
        File file = new File(path);
        HttpHeaders headers = new HttpHeaders();
        //为了解决中文名称乱码问题
        String fileName = null;
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
