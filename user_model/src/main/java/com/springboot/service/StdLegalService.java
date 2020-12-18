package com.springboot.service;

public interface StdLegalService {
    /**
     * 生成司法中间表
     * @param reqId
     */
    public void createStdLegalMidTable(String reqId);

    /**
     * 预处理标准司法数据
     * @param reqId
     */
    public void preStdSsDatas(String reqId);
}
