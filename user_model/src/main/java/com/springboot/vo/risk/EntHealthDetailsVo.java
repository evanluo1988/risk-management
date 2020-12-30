package com.springboot.vo.risk;

import lombok.Data;

import java.util.List;

/**
 * 企业健康详情
 */
@Data
public class EntHealthDetailsVo {
    /**
     * 企业注册信息
     */
    private EntRegInformationVo entRegInformation;
    /**
     * 企业经营发展信息
     */
    private List<EntAlterVo> entAlterList;
    /**
     * 专利信息
     */
    private PatentInformationVo patentInformation;
    /**
     * 商标信息
     */
    private BrandInformationVo brandInformation;
    /**
     * 软件著作权信息
     */
    private CopyrightInformationVo copyrightInformation;
    /**
     * 企业经营异常详情
     */
    private EntAbnormalDetailsVo entAbnormalDetails;
    /**
     * 企业涉诉详情
     */
    private List<LitigaCaseVo> litigaCaseList;


}
