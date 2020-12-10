package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;

@Data
@TableName("eds_gs_qygdjczxx")
public class EdsGsQygdjczxx extends BaseDomain {
    /**
     * 申请编号ID 由我方生成
     */
    @TableField(value = "req_id")
    private String reqId;
    /**
     * 业务申请编号
     */
    @TableField(value = "businessid")
    private String businessId;
    /**
     * 股东名称
     */
    @TableField(value = "shareholdername")
    private String shareholderName;
    /**
     * 认缴出资额（单位：万元）
     */
    @TableField(value = "subconam")
    private String subConAm;
    /**
     * 币种
     */
    @TableField(value = "regcapcur")
    private String subConCur;
    /**
     * 出资日期
     */
    @TableField(value = "condate")
    private String subConDate;
    /**
     * 出资比例
     */
    @TableField(value = "fundedratio")
    private String fundedRatio;
    /**
     * 国别
     */
    @TableField(value = "country")
    private String country;
}
