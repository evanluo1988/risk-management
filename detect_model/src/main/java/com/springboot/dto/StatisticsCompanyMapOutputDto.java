package com.springboot.dto;

import lombok.Data;

/**
 * @author lhf
 * @date 2021/3/2 5:34 下午
 **/
@Data
public class StatisticsCompanyMapOutputDto {
    private Long id;
    /**
     * 企业名称
     */
    private String entName;
    /**
     * 信用代码
     */
    private String creditCode;
    /**
     * 街道
     */
    private String street;
    /**
     * 经度
     */
    private String lon;
    /**
     * 纬度
     */
    private String lat;
}
