package com.qg.www.service;

import com.qg.www.dtos.RequestData;
import com.qg.www.dtos.ResponseData;
import com.qg.www.models.AwardInfo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.Request;

import java.util.List;

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
     *
     * @param data 页数、获奖年份、奖项级别、获奖等级
     * @return 奖项列表
     */
    ResponseData queryAwardInfo(RequestData data);

    /**
     * 根据ID查询成员详细信息
     *
     * @param data 成员ID
     * @return 成员详细信息
     */
    ResponseData getAwardInfoById(RequestData data);

    /**
     * 添加图片
     *
     * @param awardId 奖项编号
     * @param picture 图片文件
     * @param path    存放路径
     * @return 状态码
     */
    ResponseData addAwardInfoPicture(MultipartFile picture, String path, String awardId);

    /**
     * 查询奖项列表
     *
     * @param data 获奖年份、奖项级别、获奖等级
     * @return 奖项列表
     */
    ResponseData queryAwardInfoAndroid(RequestData data);

    /**
     * 导出分类奖项信息；
     *
     * @param data 分类数据
     * @return 文件路径；
     */
    String exportSomeAwardInfo(RequestData data);

    /**
     * 修改奖项的详细信息
     *
     * @param data 修改后的奖项信息
     * @return 状态码
     */
    ResponseData updateAwardInfo(RequestData data);
}
