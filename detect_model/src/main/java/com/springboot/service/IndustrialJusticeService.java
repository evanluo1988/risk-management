package com.springboot.service;

/**
 * 工商司法接口
 * @author evan
 */
public interface IndustrialJusticeService {
    /**
     * 1.根据企业名称获取工商司法数据
     * 2.将获取的数据导入原始表
     * 3.将原始表数据转化称标准表
     * @param entName
     * @return  reqId
     */
    public String handelData(String entName) throws Exception;

    /**
     * 计算指标值
     * @param reqId
     * @return
     */
    public void culQuotas(String reqId, String quotaType);

    void analysisJustice(String reqId) throws Exception;

}
