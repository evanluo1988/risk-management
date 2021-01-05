package com.springboot.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ia_as_partent_detail")
public class IaAsPartentDetail extends BaseDomain {
    @TableField(value = "req_id")
    private String reqId;
    @TableField(value = "ia_as_partent_id")
    private Long iaAsPartentId;
    /**
     * 附图类别
     */
    @JSONField(name = "pdt")
    @TableField(value = "pdt")
    private String pdt;
    /**
     * 被引证专利数量
     */
    @JSONField(name = "citByRefdsNum")
    @TableField(value = "citbyrefdsnum")
    private String citByRefdsNum;
    /**
     * 专利类型
     */
    @JSONField(name = "pdtStr")
    @TableField(value = "pdtstr")
    private String pdtStr;
    /**
     * 同族专利数量
     */
    @JSONField(name = "fimalyNum")
    @TableField(value = "fimalynum")
    private String fimalyNum;
    /**
     * 引证专利数量
     */
    @JSONField(name = "citsNum")
    @TableField(value = "citsnum")
    private String citsNum;

}
