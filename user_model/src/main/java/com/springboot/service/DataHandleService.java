package com.springboot.service;

import com.springboot.vo.risk.EntHealthReportVo;

public interface DataHandleService {
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
    public void culQuotas(String reqId);

    /**
     * 计算模型
     * @param reqId
     */
    public void culModels(String reqId);

    /**
     * 解析司法
     * @param reqId
     */
    public void analysisJustice(String reqId) throws Exception;

    /**
     * 通过指标获取企业健康报告
     * @param reqId
     * @return
     */
    public EntHealthReportVo getEntHealthReportVo(String reqId);
}
