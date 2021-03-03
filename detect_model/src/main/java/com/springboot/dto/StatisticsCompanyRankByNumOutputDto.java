package com.springboot.dto;

import lombok.Data;

/**
 * @author lhf
 * @date 2021/3/2 5:42 下午
 **/
@Data
public class StatisticsCompanyRankByNumOutputDto {
    /**
     * 街道
     */
    private String street;
    /**
     * 数量
     */
    private Integer num;
    /**
     * 比率
     */
    private Float ratio;

    public void calcRatio(Integer total) {
        ratio = num.floatValue()/total.floatValue();
    }
}
