package com.springboot.vo.risk;

import com.springboot.util.StrUtils;
import lombok.Data;

/**
 * 行政处罚
 */
@Data
public class EntCaseinfoVo {
    /**
     * 处罚决定书文号
     */
    private String pendecno;

    /**
     * 处罚决定书签发日期
     */
    private String pendecissdate;

    /**
     * 公示日期
     */
    private String publicdate;

    /**
     * 处罚机关
     */
    private String penauth;

    /**
     * 主要违法事实
     */
    private String illegfact;

    /**
     * 处罚种类
     */
    private String pentype;

    /**
     * 处罚结果
     */
    private String penresult;

    public String getPublicdate() {
        return StrUtils.getDataStr(publicdate);
    }
}
