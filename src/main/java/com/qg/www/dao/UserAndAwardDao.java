package com.qg.www.dao;

import com.qg.www.dtos.RequestData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author linxu
 * @version 1.1
 * 成员、奖项信息关联表
 */
public interface UserAndAwardDao {
    /**
     * 通过成员ID查询与之关联的奖项的ID
     *
     * @param data 包含成员的ID
     * @return 奖项ID列表
     */
    List<Integer> queryAwardInfoId(RequestData data);

    /**
     * 添加关联
     *
     * @param userId  成员ID
     * @param awardId 奖项ID
     * @throws Exception 异常
     */
    void addUserAndAwardReaction(@Param("userId") Integer userId, @Param("awardId") Integer awardId) throws Exception;
}
