package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @Author 刘宏飞
 * @Date 2020/12/29 10:20
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("std_ia_partent")
public class StdIaPartent extends BaseDomain {
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
     * 专利编码
     */
    @TableField("pid")
    private String pid;
    /**
     * 标题-中文
     */
    @TableField("tic")
    private String tic;
    /**
     * 标题-英文
     */
    @TableField("tie")
    private String tie;
    /**
     * 标题-原始
     */
    @TableField("tio")
    private String tio;
    /**
     * 申请号-原始
     */
    @TableField("ano")
    private String ano;
    /**
     * 申请日
     */
    @TableField("ad")
    private LocalDate ad;
    /**
     * 公开日
     */
    @TableField("pd")
    private LocalDate pd;
    /**
     * 文献类型
     */
    @TableField("pk")
    private String pk;
    /**
     * 文献号-原始
     */
    @TableField("pno")
    private String pno;
    /**
     *申请人-原始
     */
    @TableField("apo")
    private String apo;
    /**
     * 申请人-英文
     */
    @TableField("ape")
    private String ape;
    /**
     * 申请人-中文
     */
    @TableField("apc")
    private String apc;
    /**
     * ipc
     */
    @TableField("ipc")
    private String ipc;
    /**
     * 洛迦诺分类
     */
    @TableField("lc")
    private String lc;
    /**
     * 专利强度
     */
    @TableField("vu")
    private String vu;
    /**
     * 摘要-原始语种
     */
    @TableField("abso")
    private String abso;
    /**
     * 摘要-英文
     */
    @TableField("abse")
    private String abse;
    /**
     * 摘要-中文
     */
    @TableField("absc")
    private String absc;
    /**
     * 缩略图
     */
    @TableField("imgtitle")
    private String imgTitle;
    /**
     * 缩略图名称
     */
    @TableField("imgname")
    private String imgName;
    /**
     * 当前权利状态
     */
    @TableField("lssc")
    private String lssc;
    /**
     * 专利类型
     */
    @TableField("pdt")
    private String pdt;
    /**
     * 简要说明-中文
     */
    @TableField("debec")
    private String debec;
    /**
     * 简要说明-原始
     */
    @TableField("debeo")
    private String debeo;
    /**
     * 简要说明-英文
     */
    @TableField("debee")
    private String debee;
    /**
     * 原始图
     */
    @TableField("imgo")
    private String imgo;
    /**
     * 是否存在 PDF
     */
    @TableField("pdfexist")
    private String pdfExist;
    /**
     * 申请号标准
     */
    @TableField("ans")
    private String ans;
    /**
     * 公布号标准
     */
    @TableField("pns")
    private String pns;
    /**
     * 代表性文献号
     */
    @TableField("sfpns")
    private String sfpns;
    /**
     * 发明人-中文
     */
    @TableField("inc")
    private String inc;
    /**
     * 发明人-英文
     */
    @TableField("ine")
    private String ine;
    /**
     * 发明人-原文
     */
    @TableField("ino")
    private String ino;
    /**
     * 代理人-中文
     */
    @TableField("agc")
    private String agc;
    /**
     * 代理人-英文
     */
    @TableField("age")
    private String age;
    /**
     * 代理人-原文
     */
    @TableField("ago")
    private String ago;
    /**
     * 专利权人-中文
     */
    @TableField("iasc")
    private String iasc;
    /**
     * 专利权人-英文
     */
    @TableField("ase")
    private String ase;
    /**
     * 专利权人-原文
     */
    @TableField("aso")
    private String aso;
    /**
     * 审查员-中文
     */
    @TableField("exc")
    private String exc;
    /**
     * 审查员人-英文
     */
    @TableField("exe")
    private String exe;
    /**
     * 审查员-原文
     */
    @TableField("exo")
    private String exo;
}
