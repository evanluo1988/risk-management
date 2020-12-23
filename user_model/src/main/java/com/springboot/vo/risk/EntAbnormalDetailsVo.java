package com.springboot.vo.risk;

import lombok.Data;

import java.util.List;

/**
 * 企业经营异常详情
 */
@Data
public class EntAbnormalDetailsVo {
    /**
     * 行政处罚
     */
    private List<EntCaseinfoVo> caseinfoList;
    /**
     * 股权冻结
     */
    private List<EntSharesfrostVo> entSharesfrostList;
    /**
     * 企业异常名录
     */
    private List<EntExceptionVo> entExceptionList;
    /**
     * 企业清算信息
     */
    private List<EntLiquidationVo> entLiquidationList;

}
