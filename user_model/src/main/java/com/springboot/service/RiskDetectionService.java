package com.springboot.service;

import com.springboot.enums.OrgEnum;
import com.springboot.vo.risk.EntHealthReportVo;

public interface RiskDetectionService {
    /**
     * 检测企业
     * @param entName 企业名称
     */
    public EntHealthReportVo checkByEntName(String entName, OrgEnum org);

    public String getEntAddress(String entName);
}
