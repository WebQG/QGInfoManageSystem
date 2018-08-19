package com.qg.www.dao;

import com.qg.www.models.AwardInfo;


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
}
