package com.springboot.domain.risk;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ia_as_partent_catalog")
public class IaAsPartentCatalog extends BaseDomain {
    @TableField(value = "req_id")
    private String reqId;
    @TableField(value = "ia_as_partent_detail_id")
    private Long iaAsPartentDetailId;
    /**
     * 专利编码
     */
    @JSONField(name = "pid")
    @TableField(value = "pid")
    private String pid;
    /**
     * 自引专利数量
     */
    @JSONField(name = "cipca")
    @TableField(value = "cipca")
    private String cipca;
    /**
     * 他引专利数量
     */
    @JSONField(name = "cipct")
    @TableField(value = "cipct")
    private String cipct;
    /**
     * 诉讼次数
     */
    @JSONField(name = "plic")
    @TableField(value = "plic")
    private String plic;
    /**
     * 权利要求数量
     */
    @JSONField(name = "cln")
    @TableField(value = "cln")
    private String cln;
    /**
     * 当前权利状态
     */
    @JSONField(name = "lssc")
    @TableField(value = "lssc")
    private String lssc;
    /**
     * 独权数
     */
    @JSONField(name = "icln")
    @TableField(value = "icln")
    private String icln;
    /**
     * 许可次数
     */
    @JSONField(name = "pcc")
    @TableField(value = "pcc")
    private String pcc;
    /**
     * 转让次数
     */
    @JSONField(name = "tcc")
    @TableField(value = "tcc")
    private String tcc;
    /**
     * 无效次数
     */
    @JSONField(name = "pinc")
    @TableField(value = "pinc")
    private String pinc;
    /**
     * PCT 专利
     */
    @JSONField(name = "pct")
    @TableField(value = "pct")
    private String pct;
    /**
     * 质押次数
     */
    @JSONField(name = "ppc")
    @TableField(value = "ppc")
    private String ppc;
    /**
     * 扩展同族专利数量
     */
    @JSONField(name = "efc")
    @TableField(value = "efc")
    private String efc;
    /**
     * 被印证数量
     */
    @JSONField(name = "cigc")
    @TableField(value = "cigc")
    private String cigc;
    /**
     * 从权数
     */
    @JSONField(name = "dcln")
    @TableField(value = "dcln")
    private String dcln;
    /**
     * 同族专利数量
     */
    @JSONField(name = "sfc")
    @TableField(value = "sfc")
    private String sfc;
    /**
     * 引证专利数量
     */
    @JSONField(name = "cipc")
    @TableField(value = "cipc")
    private String cipc;
}
