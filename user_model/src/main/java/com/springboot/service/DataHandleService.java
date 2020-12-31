package com.springboot.service;

import com.springboot.vo.risk.EntHealthReportVo;

public interface DataHandleService {
    /**
     * 1.根据企业名称获取工商司法和知识产权数据
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
     * 通过指标获取企业健康报告
     * @param reqId
     * @return
     */
    public EntHealthReportVo getEntHealthReportVo(String reqId);
}
