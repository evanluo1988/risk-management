package com.springboot.domain.risk;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ia_as_partent")
public class IaAsPartent extends BaseDomain {
    @TableField(value = "req_id")
    private String reqId;
    /**
     * 专利编码
     */
    @JSONField(name = "pid")
    @TableField(value = "pid")
    private String pid;
    /**
     * 标题-中文
     */
    @JSONField(name = "tic")
    @TableField(value = "tic")
    private String tic;
    /**
     * 标题-英文
     */
    @JSONField(name = "tie")
    @TableField(value = "tie")
    private String tie;
    /**
     * 标题-原始
     */
    @JSONField(name = "tio")
    @TableField(value = "tio")
    private String tio;
    /**
     * 申请号-原始
     */
    @JSONField(name = "ano")
    @TableField(value = "ano")
    private String ano;
    /**
     * 申请日
     */
    @JSONField(name = "ad")
    @TableField(value = "ad")
    private String ad;
    /**
     * 公开日
     */
    @JSONField(name = "pd")
    @TableField(value = "pd")
    private String pd;
    /**
     * 文献类型
     */
    @JSONField(name = "pk")
    @TableField(value = "pk")
    private String pk;
    /**
     * 文献号-原始
     */
    @JSONField(name = "pno")
    @TableField(value = "pno")
    private String pno;
    /**
     * 申请人-原始
     */
    @JSONField(name = "apo")
    @TableField(value = "apo")
    private String apo;
    /**
     * 申请人-英文
     */
    @JSONField(name = "ape")
    @TableField(value = "ape")
    private String ape;
    /**
     * 申请人-中文
     */
    @JSONField(name = "apc")
    @TableField(value = "apc")
    private String apc;
    /**
     * IPC
     */
    @JSONField(name = "ipc")
    @TableField(value = "ipc")
    private String ipc;
    /**
     * 洛迦诺分类
     */
    @JSONField(name = "lc")
    @TableField(value = "lc")
    private String lc;
    /**
     * 专利强度
     */
    @JSONField(name = "vu")
    @TableField(value = "vu")
    private String vu;
    /**
     * 摘要-原始语种
     */
    @JSONField(name = "abso")
    @TableField(value = "abso")
    private String abso;
    /**
     * 摘要-英文
     */
    @JSONField(name = "abse")
    @TableField(value = "abse")
    private String abse;
    /**
     * 摘要-中文
     */
    @JSONField(name = "absc")
    @TableField(value = "absc")
    private String absc;
    /**
     * 缩略图
     */
    @JSONField(name = "imgtitle")
    @TableField(value = "imgtitle")
    private String imgTitle;
    /**
     * 缩略图名称
     */
    @JSONField(name = "imgname")
    @TableField(value = "imgname")
    private String imgName;
    /**
     * 当前权利状态
     */
    @JSONField(name = "lssc")
    @TableField(value = "lssc")
    private String lssc;
    /**
     * 专利类型
     */
    @JSONField(name = "pdt")
    @TableField(value = "pdt")
    private String pdt;
    /**
     * 简要说明-中文
     */
    @JSONField(name = "debec")
    @TableField(value = "debec")
    private String debec;
    /**
     * 简要说明-原始
     */
    @JSONField(name = "debeo")
    @TableField(value = "debeo")
    private String debeo;
    /**
     * 简要说明-英文
     */
    @JSONField(name = "debee")
    @TableField(value = "debee")
    private String debee;
    /**
     * 原始图
     */
    @JSONField(name = "imgo")
    @TableField(value = "imgo")
    private String imgo;
    /**
     * 是否存在 PDF
     */
    @JSONField(name = "pdfexist")
    @TableField(value = "pdfexist")
    private String pdfExist;
    /**
     * 申请号标准
     */
    @JSONField(name = "ans")
    @TableField(value = "ans")
    private String ans;
    /**
     * 公布号标准
     */
    @JSONField(name = "pns")
    @TableField(value = "pns")
    private String pns;
    /**
     * 代表性文献号
     */
    @JSONField(name = "sfpns")
    @TableField(value = "sfpns")
    private String sfpns;
    /**
     * 发明人-中文
     */
    @JSONField(name = "inc")
    @TableField(value = "inc")
    private String inc;
    /**
     * 发明人-英文
     */
    @JSONField(name = "ine")
    @TableField(value = "ine")
    private String ine;
    /**
     * 发明人-原文
     */
    @JSONField(name = "ino")
    @TableField(value = "ino")
    private String ino;
    /**
     * 代理人-中文
     */
    @JSONField(name = "agc")
    @TableField(value = "agc")
    private String agc;
    /**
     * 代理人-英文
     */
    @JSONField(name = "age")
    @TableField(value = "age")
    private String age;
    /**
     * 代理人-原文
     */
    @JSONField(name = "ago")
    @TableField(value = "ago")
    private String ago;
    /**
     * 专利权人-中文
     */
    @JSONField(name = "asc")
    @TableField(value = "iasc")
    private String iasc;
    /**
     * 专利权人-英文
     */
    @JSONField(name = "ase")
    @TableField(value = "ase")
    private String ase;
    /**
     * 专利权人-原文
     */
    @JSONField(name = "aso")
    @TableField(value = "aso")
    private String aso;
    /**
     * 审查员-中文
     */
    @JSONField(name = "exc")
    @TableField(value = "exc")
    private String exc;
    /**
     * 审查员-英文
     */
    @JSONField(name = "exe")
    @TableField(value = "exe")
    private String exe;
    /**
     * 审查员-原文
     */
    @JSONField(name = "exo")
    @TableField(value = "exo")
    private String exo;

}
