package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author 刘宏飞
 * @Date 2020/12/16 15:18
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName(value = "std_ent_sharesfrost_list")
public class StdEntSharesfrost extends BaseDomain {
    private String reqId;

    /**
     * 冻结文号
     */
    private String frodocno;

    /**
     * 冻结机关
     */
    private String froauth;

    /**
     * 冻结起始日期
     */
    private String frofrom;

    /**
     * 冻结截止日期
     */
    private String froto;

    /**
     * 解冻日期
     */
    private String thawdate;

    /**
     * 冻结金额
     */
    private String froam;

    /**
     * 解冻文号
     */
    private String thawdocno;

    /**
     * 解冻说明
     */
    private String thawcomment;
}
