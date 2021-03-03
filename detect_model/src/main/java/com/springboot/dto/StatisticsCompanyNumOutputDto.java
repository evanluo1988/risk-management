package com.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统计企业数量
 * @author lhf
 * @date 2021/3/2 5:13 下午
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsCompanyNumOutputDto {
    /**
     * 企业数量
     */
    private Integer total;
    /**
     * 今年新增数量
     */
    private Integer annualGrowth;
}
