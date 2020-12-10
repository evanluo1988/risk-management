package com.springboot.service;

public interface DataHandleService {
    /**
     * 1.根据企业名称获取工商司法数据
     * 2.将获取的数据导入原始表
     * 3.将原始表数据转化称标准表
     * @param entName
     * @return  reqId
     */
    public String handelData(String entName);

    /**
     * 通过企业名称计算指标值
     * @param reqId
     * @return
     */
    public void culQuotas(String reqId);

    /**
     * 预处理标准司法数据
     * @param reqId
     */
    public void preStdSsDatas(String reqId);

}
