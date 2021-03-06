package com.qg.www.controllers;

import com.qg.www.dtos.RequestData;
import com.qg.www.dtos.ResponseData;
import com.qg.www.service.AwardService;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
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
 * 奖项控制器；
 */
@RequestMapping("/awardinfo")
@RestController
@CrossOrigin
public class AwardInfoController {
    @Resource
    private AwardService service;

    @PostMapping("/import")
    public ResponseData importAwardInfo(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String path = request.getServletContext().getRealPath("/");
        return service.importExcel(file, path);
    }

    /**
     * 导出EXCEL文件
     *
     * @return EXCEL文件
     */
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportAwardInfo(@Param("awardInfoId") String awardInfoId) throws IOException {
        String path = service.exportExcel(awardInfoId);
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
                headers, HttpStatus.OK);
    }

    /**
     * 查询奖项列表
     *
     * @param data 页数、获奖年份、奖项级别、获奖等级
     * @return 奖项列表
     */
    @PostMapping("/queryawardinfo")
    public ResponseData queryAwardInfo(@RequestBody RequestData data) {
        return service.queryAwardInfo(data);
    }

    @PostMapping("/getawardinfo")
    public ResponseData getAwardInfo(@RequestBody RequestData data) {
        return service.getAwardInfoById(data);
    }

    /**
     * 添加图片信息
     *
     * @param file    图片
     * @param request 请求
     * @return 状态码
     */
    @PostMapping("/modifypicture")
    public ResponseData addAwardInfoPicture(@RequestParam("picture") MultipartFile file, HttpServletRequest request, @RequestParam(value = "awardId", required = false) String awardId) {
        String path = request.getServletContext().getRealPath("");
        System.out.println("上传奖项图片ID：" + awardId);
        return service.addAwardInfoPicture(file, path, awardId);
    }

    /**
     * 查询奖项列表
     *
     * @param data 获奖年份、奖项级别、获奖等级
     * @return 奖项列表
     */
    @PostMapping("queryawardinfoandroid")
    public ResponseData queryAwardInfoAndroid(@RequestBody RequestData data) {
        return service.queryAwardInfoAndroid(data);
    }

    /**
     * 分类导出奖项信息；
     *
     * @return EXCEL文件
     * @throws IOException IO异常
     */
    @GetMapping("/exportsomeaward")
    public ResponseEntity<byte[]> exportSomeAward(@Param("rank") String rank, @Param("awardTime") String awardTime, @Param("awardLevel") String awardLevel) throws IOException {
        RequestData data = new RequestData();
        data.setAwardTime(awardTime);
        data.setAwardLevel(awardLevel);
        data.setRank(rank);
        String path = service.exportSomeAwardInfo(data);
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
                headers, HttpStatus.OK);
    }

    /**
     * 修改奖项的详细信息
     *
     * @param data 修改后的奖项信息
     * @return 状态码
     */
    @PostMapping("/updateawardinfo")
    public ResponseData updateAwardInfo(@RequestBody RequestData data) {
        return service.updateAwardInfo(data);
    }

    /**
     * 删除奖项信息
     *
     * @param data 奖项编号
     * @return 状态码
     */
    @PostMapping("/delete")
    public ResponseData deleteAwardInfo(@RequestBody RequestData data, HttpServletRequest request) {
        //获取权限；
        Integer privilege = (Integer) request.getSession().getAttribute("privilege");
        return service.deleteAwardInfo(data, privilege);
    }

    /**
     * 为奖项手动添加成员的信息
     *
     * @param data    奖项ID，成员ID列表
     * @param request 请求
     * @return 状态码
     */
    @PostMapping("/addreaction")
    public ResponseData addUserForAward(@RequestBody RequestData data, HttpServletRequest request) {
        //获取权限；
        Integer privilege = (Integer) request.getSession().getAttribute("privilege");
        return service.addUserForAward(data,privilege);
    }
}
