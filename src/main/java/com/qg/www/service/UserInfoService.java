package com.qg.www.service;

import com.qg.www.dtos.ResponseData;

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
     * @return 状态码
     */
    ResponseData importExcel();
}
