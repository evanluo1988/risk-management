package com.springboot.domain.risk;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(exclude = {"id","createTime","createBy","updateTime","updateBy","reqId","businessId"})
@TableName("ent_wy_alterlist")
public class EntWyAlter extends BaseDomain {
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
     * 变更事项
     */
    @TableField(value = "altitem")
    @JSONField(name = "altItem")
    private String altItem;
    /**
     * 变更日期
     */
    @TableField(value = "altdate")
    @JSONField(name = "altDate")
    private String altDate;
    /**
     * 变更前内容
     */
    @TableField(value = "altbe")
    @JSONField(name = "altBe")
    private String altBe;
    /**
     * 变更后内容
     */
    @TableField(value = "altaf")
    @JSONField(name = "altAf")
    private String altAf;

}
