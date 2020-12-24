package com.springboot.vo.risk;

import com.springboot.util.StrUtils;
import lombok.Data;

/**
 * 企业清算信息
 */
@Data
public class EntLiquidationVo {
    /**
     * 清算负责人
     */
    private String ligprincipal;

    /**
     * 清算组成员
     */
    private String liqmen;

    /**
     * 清算完结情况
     */
    private String ligst;

    /**
     * 清算完结日期
     */
    private String ligenddate;

    /**
     * 债务承接人
     */
    private String debttranee;

    /**
     * 债权承接人
     */
    private String claimtranee;

    public String getLigenddate() {
        return StrUtils.getDataStr(ligenddate);
    }
}
