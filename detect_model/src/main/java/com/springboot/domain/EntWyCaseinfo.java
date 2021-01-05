package com.springboot.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author 刘宏飞
 * @Date 2020/12/15 14:05
 * @Version 1.0
 */
@Data
@ToString
@EqualsAndHashCode(exclude = {"id","createTime","createBy","updateTime","updateBy","reqId","businessId"})
@TableName(value = "ent_wy_caseinfolist")
public class EntWyCaseinfo extends BaseDomain {
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
     * 处罚决定书文号
     */
    @TableField(value = "pendecno")
    @JSONField(name = "penDecNo")
    private String penDecNo;

    /**
     * 主要违法事实
     */
    @TableField(value = "illegfact")
    @JSONField(name = "illegFact")
    private String illegFact;

    /**
     * 处罚种类
     */
    @TableField(value = "pentype")
    @JSONField(name = "penType")
    private String penType;

    /**
     * 处罚种类中文
     */
    @TableField(value = "pentypecn")
    @JSONField(name = "penTypeCn")
    private String penTypeCn;

    /**
     * 处罚结果
     */
    @TableField(value = "penresult")
    @JSONField(name = "penResult")
    private String penResult;

    /**
     * 处罚机关
     */
    @TableField(value = "penauth")
    @JSONField(name = "penAuth")
    private String penAuth;

    /**
     * 处罚机关名称
     */
    @TableField(value = "penauthcn")
    @JSONField(name = "penAuthCn")
    private String penAuthCn;

    /**
     * 处罚决定书签发日期
     */
    @TableField(value = "pendecissdate")
    @JSONField(name = "penDecissDate")
    private String penDecissDate;

    /**
     * 公示日期
     */
    @TableField(value = "publicdate")
    @JSONField(name = "publicDate")
    private String publicDate;

}
