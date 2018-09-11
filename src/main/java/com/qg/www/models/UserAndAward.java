package com.qg.www.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * 中间表模型
 *
 * @author linxu
 * @version 1.1
 */
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAndAward {
    private Integer id;
    private Integer awardInfoId;
    private Integer userInfoId;
}
