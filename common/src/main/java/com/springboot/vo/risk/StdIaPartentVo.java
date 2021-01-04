package com.springboot.vo.risk;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StdIaPartentVo {
    /**
     * 申请公布日
     */
    private LocalDate pd;
    /**
     * 申请公布号
     */
    private String pns;
    /**
     * 专利名称
     */
    private String tic;
    /**
     * 专利类型
     */
    private String pdt;
    /**
     * 权利状态
     */
    private String lssc;

    public String getLssc() {
        if("1".equals(lssc)) {
            return "有效";
        } else if("2".equals(lssc)) {
            return "无效";
        } else if("3".equals(lssc)) {
            return "在审";
        }
        return lssc;
    }
}
