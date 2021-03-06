package com.springboot.vo.risk;

import lombok.Data;

import java.util.List;

/**
 * 商标信息
 */
@Data
public class BrandInformationVo {
    /**
     * 无效商标总数
     */
    private String invalidBrandNum;
    /**
     * 无效商标种类分布
     */
    private String invalidBrandSpeciesDistribution;
    /**
     * 无效商标种类和数量
     */
    private List<BrandVarietyVo> invalidBrandVarietyList;
    /**
     * 有效商标总数
     */
    private String validBrandNum;
    /**
     * 有效商标种类分布
     */
    private String validBrandSpeciesDistribution;
    /**
     * 有效商标种类和数量
     */
    private List<BrandVarietyVo> validBrandVarietyList;
    /**
     * 商标明细
     */
    private List<StdIaBrandVo> stdIaBrandList;
}
