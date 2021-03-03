package com.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lhf
 * @date 2021/3/3 1:49 下午
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsCompanyOperatingStatusOutputDto {
    /**
     * 经营状态
     */
    private String operatingStatus;
    /**
     * 数量
     */
    private Integer num;
}
