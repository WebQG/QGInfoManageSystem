package com.qg.www.dao;

import com.qg.www.dtos.RequestData;
import com.qg.www.models.AwardInfo;
import org.apache.ibatis.session.RowBounds;


import java.util.List;

/**
 * @author net
 * @version 1.0
 * 奖项信息dao接口
 */
public interface AwardInfoDao {
    /**
     * 查询奖项信息
     *
     * @return 奖项信息列表
     */
    List<AwardInfo> queryAwardInfo();

    /**
     * 添加奖项信息
     *
     * @param awardInfo 奖项信息
     * @return 成功条数
     */
    int addAwardInfo(AwardInfo awardInfo);

    /**
     * 查询奖项列表
     *
     * @param data 页数、获奖年份、奖项级别、获奖等级
     * @param rowBounds 分页参数
     * @return 奖项列表
     */
    List<AwardInfo> queryiAppointedAwardInfo(RequestData data, RowBounds rowBounds);
}