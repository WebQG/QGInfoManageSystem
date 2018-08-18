package com.qg.www.dao;

import com.qg.www.models.AwardInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
     * @param awardTime  获奖时间
     * @param awardLevel 奖项级别
     * @param rank       获奖等级
     * @return 奖项信息列表
     */
    List<AwardInfo> queryAwardInfo(@Param("awardTime") String awardTime,@Param("awardLevel") String awardLevel,@Param("rank") String rank);
}
