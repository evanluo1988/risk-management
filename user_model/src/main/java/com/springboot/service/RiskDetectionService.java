package com.springboot.service;

public interface RiskDetectionService {
    /**
     * 检测企业
     * @param entName 企业名称
     */
    public void checkByEntName(String entName);

    public String getEntAddress(String entName);
}
