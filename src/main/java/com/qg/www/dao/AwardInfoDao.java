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
     * @param data      页数、获奖年份、奖项级别、获奖等级
     * @param rowBounds 分页参数
     * @return 奖项列表
     */
    List<AwardInfo> queryAppointedAwardInfo(RequestData data, RowBounds rowBounds);

    /**
     * 根据ID查询成员详细信息
     *
     * @param data 成员ID
     * @return 成员详细信息
     */
    AwardInfo getAwardInfoById(RequestData data);

    /**
     * 通过名称查询奖项粗略信息
     *
     * @param data      奖项名称
     * @param rowBounds 分页信息
     * @return 编号、名称、时间、参赛学生、图片地址
     */
    List<AwardInfo> queryAwardInfoByName(RequestData data, RowBounds rowBounds);
}
