package com.qg.www.service;

import com.qg.www.dtos.RequestData;
import com.qg.www.dtos.ResponseData;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author net
 * @version 1.0
 * 奖项业务接口
 */
public interface AwardService {
    /**
     * 导出excel表格业务
     *
     * @return 文件路径
     */
    String exportExcel();

    /**
     * 奖项信息导入业务
     *
     * @param file     文件
     * @param filePath 文件路径
     * @return 状态码
     */
    ResponseData importExcel(MultipartFile file, String filePath);

    /**
     * 查询奖项列表
     * @param data 页数、获奖年份、奖项级别、获奖等级
     * @return 奖项列表
     */
    ResponseData queryAwardInfo(RequestData data);
}