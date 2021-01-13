package com.springboot.vo.risk;

import com.springboot.utils.StrUtils;
import lombok.Data;

/**
 * 股权冻结
 */
@Data
public class EntSharesfrostVo {
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

    public String getFrofrom() {
        return StrUtils.getDataStr(frofrom);
    }

    public String getFroto() {
        return StrUtils.getDataStr(froto);
    }

    public String getThawdate() {
        return StrUtils.getDataStr(thawdate);
    }

    public String getFroam() {
        return StrUtils.getMoneyText(froam, null);
    }
}
