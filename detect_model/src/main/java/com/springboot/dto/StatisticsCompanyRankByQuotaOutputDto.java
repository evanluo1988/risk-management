package com.springboot.dto;

import lombok.Data;

/**
 * @author lhf
 * @date 2021/3/3 10:08 上午
 **/
@Data
public class StatisticsCompanyRankByQuotaOutputDto {
    /**
     * 企业名称
     */
    private String entName;
    /**
     * 数量
     */
    private Integer num;
}
