package com.springboot.vo.risk;

import com.springboot.utils.StrUtils;
import lombok.Data;

/**
 * 企业异常名录
 */
@Data
public class EntExceptionVo {
    /**
     * 企业名称
     */
    private String entname;

    /**
     * 列入日期
     */
    private String indate;

    /**
     * 列入原因
     */
    private String inreason;

    /**
     * 移出日期
     */
    private String outdate;

    /**
     * 移出异常名录原因
     */
    private String outreason;

    public String getIndate() {
        return StrUtils.getDataStr(indate);
    }

    public String getOutdate() {
        return StrUtils.getDataStr(outdate);
    }
}
