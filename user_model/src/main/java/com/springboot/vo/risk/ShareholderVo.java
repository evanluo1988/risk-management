package com.springboot.vo.risk;

import lombok.Data;

/**
 * 股东信息
 */
@Data
public class ShareholderVo {
    /**
     * 股东名称
     */
    private String shareHolderName;

    /**
     * 认缴出资额
     */
    private String subconam;

    /**
     * 出资日期
     */
    private String conDate;

    /**
     * 出资比例
     */
    private String fundedratio;

    /**
     * 股东类型
     */
    private String invtype;

    /**
     * 出资方式
     */
    private String conform;
}
