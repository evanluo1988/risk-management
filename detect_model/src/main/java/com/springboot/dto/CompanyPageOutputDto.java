package com.springboot.dto;

import lombok.Data;

/**
 * @author lhf
 * @date 2021/3/3 2:25 下午
 **/
@Data
public class CompanyPageOutputDto {
    /**
     * 公司ID
     */
    private Long id;
    /**
     * 公司名
     */
    private String entName;
    /**
     * 经营状态
     */
    private String operatingStatus;
    /**
     * 街道
     */
    private String street;
    /**
     * 法人
     */
    private String fr;
    /**
     * 信用代码
     */
    private String creditCode;
    /**
     * 员工人数
     */
    private String employeesNum;
    /**
     * 注册时间
     */
    private String regDate;
    /**
     * 经营稳定性综合得分
     */
    private double businessStabilityScore;
    /**
     * 知识产权价值度综合得分
     */
    private double intellectualPropertyScore;
    /**
     * 经营风险综合得分
     */
    private double businessRiskScore;
    /**
     * 法律风险综合得分
     */
    private double legalRiskScore;
    /**
     * 专利总数
     */
    private Integer patent;
    /**
     * 软著数量
     */
    private Integer soft;
    /**
     * 工商处罚次数
     */
    private Integer businessPenalty;
}
