package com.springboot.service;

/**
 * 知识产权
 */
public interface IntellectualPropertyService {
    /**
     * 1.根据企业名称获取知识产权数据
     * 2.将获取的数据导入原始表
     * 3.将原始表数据转化称标准表
     * @param entName
     * @return  reqId
     */
    public void handelData(String entName) throws Exception;

    /**
     * 计算指标值
     * @param reqId
     * @return
     */
    public void culQuotas(String reqId, String quotaType);
}
