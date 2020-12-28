package com.springboot.service;

import com.springboot.domain.risk.IaAsBrand;
import com.springboot.domain.risk.IaAsCopyright;

import java.util.List;

public interface WYSourceDataService {
    /**
     * 通过企业名称获取工商司法数据
     * @param entName
     * @return
     */
    public String getIndustrialAndJusticeData(String entName);

    /**
     * 获取专利
     * @param entName
     * @return
     */
    public String getPatentData(String entName);

    /**
     * 商标
     * @param entName
     * @return
     */
    public List<IaAsBrand> getBrandData(String entName);

    /**
     * 著作
     * @param entName
     * @return
     */
    public List<IaAsCopyright> getCopyrightData(String entName);

}
