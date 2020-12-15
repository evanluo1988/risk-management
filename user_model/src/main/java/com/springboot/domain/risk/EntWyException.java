package com.springboot.domain.risk;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;

/**
 * @Author 刘宏飞
 * @Date 2020/12/15 13:46
 * @Version 1.0
 */
@Data
@TableName("ent_wy_exceptionlist")
public class EntWyException extends BaseDomain {
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
     * 企业名称
     */
    @TableField(value = "excepentname")
    @JSONField(name = "excepEntName")
    private String excepEntName;

    /**
     * 统一社会信用代码
     */
    @TableField(value = "excepcreditcode")
    @JSONField(name = "excepCreditCode")
    private String excepCreditCode;

    /**
     * 工商注册号
     */
    @TableField(value = "excepregno")
    @JSONField(name = "excepRegNo")
    private String excepRegNo;

    /**
     * 列入原因
     */
    @TableField(value = "excepinreason")
    @JSONField(name = "excepInReason")
    private String excepInReason;

    /**
     * 列入日期
     */
    @TableField(value = "excepindate")
    @JSONField(name = "excepInDate")
    private String excepInDate;

    /**
     * 列入机关名称
     */
    @TableField(value = "excepinregorg")
    @JSONField(name = "excepInRegOrg")
    private String excepInRegOrg;

    /**
     * 退出异常名录原因
     */
    @TableField(value = "excepoutreason")
    @JSONField(name = "excepOutReason")
    private String excepOutReason;

    /**
     * 移出日期
     */
    @TableField(value = "excepoutdate")
    @JSONField(name = "excepOutDate")
    private String excepOutDate;

    /**
     * 移出机关名称
     */
    @TableField(value = "excepoutregorg")
    @JSONField(name = "excepOutRegOrg")
    private String excepOutRegOrg;
}
