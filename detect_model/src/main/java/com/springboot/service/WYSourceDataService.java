package com.springboot.service;

import com.springboot.domain.CloudQueryLog;
import com.springboot.domain.IaAsBrand;
import com.springboot.domain.IaAsCopyright;
import com.springboot.model.IaAsPartentModel;

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
    public List<IaAsPartentModel> getPatentData(String entName, CloudQueryLog cloudQueryLog);

    /**
     * 商标
     * @param entName
     * @return
     */
    public List<IaAsBrand> getBrandData(String entName, CloudQueryLog cloudQueryLog);

    /**
     * 著作
     * @param entName
     * @return
     */
    public List<IaAsCopyright> getCopyrightData(String entName, CloudQueryLog cloudQueryLog);

}
