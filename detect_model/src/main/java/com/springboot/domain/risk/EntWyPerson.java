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
@TableName("ent_wy_personlist")
public class EntWyPerson extends BaseDomain {
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
     * 职务
     */
    @TableField(value = "position")
    @JSONField(name = "position")
    private String position;
    /**
     * 企业名称
     */
    @TableField(value = "enterprisename")
    @JSONField(name = "enterpriseName")
    private String enterpriseName;
    /**
     * 人员总数量
     */
    @TableField(value = "personamount")
    @JSONField(name = "personAmount")
    private String personAmount;
    /**
     * 姓名
     */
    @TableField(value = "name")
    @JSONField(name = "name")
    private String name;
}
