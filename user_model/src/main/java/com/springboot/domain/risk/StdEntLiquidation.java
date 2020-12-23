package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author 刘宏飞
 * @Date 2020/12/16 15:35
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName(value = "std_ent_liquidation_list")
public class StdEntLiquidation extends BaseDomain {
    private String reqId;

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
}
