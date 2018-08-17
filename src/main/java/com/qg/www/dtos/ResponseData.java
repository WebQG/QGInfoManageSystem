package com.qg.www.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * @author net
 * @version 1.0
 * 数据传输对象类；
 */
@Getter
@Setter
@Service
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData {
}
