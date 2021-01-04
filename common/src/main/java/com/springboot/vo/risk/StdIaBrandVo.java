package com.springboot.vo.risk;

import lombok.Data;

import java.time.LocalDate;

/**
 * 商标明细
 */
@Data
public class StdIaBrandVo {
    /**
     * 商标
     */
    private String graph;
    /**
     * 商标名
     */
    private String brandName;
    /**
     * 尼斯分类
     */
    private String niceClassify;
    /**
     * 申请日期
     */
    private LocalDate registrationDate;
    /**
     * 注册号
     */
    private String applicationNumber;
    /**
     * 状态
     */
    private String authorityStatus;
    /**
     * 代理人
     */
    private String agentName;
}
