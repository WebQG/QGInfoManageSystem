package com.qg.www.service;

import com.qg.www.dtos.RequestData;
import com.qg.www.dtos.ResponseData;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author net
 * @version 1.0
 * 成员信息业务接口
 */
public interface UserInfoService {
    /**
     * 导出excel表格业务
     *
     * @return 文件路径
     */
    String exportExcel();

    /**
     * 导入excel文件
     *
     * @param file     文件
     * @param filePath 文件存在目录
     * @return 状态码
     */
    ResponseData importExcel(MultipartFile file, String filePath);

    /**
     * 根据关键字查询成员信息
     * @param data 所属组别、所属年级
     * @return 编号、名字、组别、年级、图片地址
     */
    ResponseData queryUserInfo(RequestData data);

    /**
     * 根据ID查找成员信息
     * @param data 成员ID
     * @return 成员详细信息
     */
    ResponseData getUserInfo(RequestData data);
    /**
     * 添加成员头像
     *
     * @param picture    照片
     * @param path       路径
     * @param userInfoId 用户信息ID
     * @return 状态码
     */
    ResponseData addUserInfoPicture(MultipartFile picture, String path, String userInfoId);

    /**
     * 提供给安卓的成员信息搜索接口
     * @param data key 1为模糊搜索 2为精确搜索 、所属组别、所属年级
     * @return 编号、名字、组别、年级、图片地址
     */
    ResponseData queryUserInfoAndroid(RequestData data);

    /**
     * 导出分类成员信息
     * @param requestData 请求信息参数
     * @return 文件路径
     */
    String exportSomeOneInfoExcel(RequestData requestData);

}
