package com.springboot.service;

import com.springboot.domain.StdLegalEnterpriseExecuted;

import java.util.List;

public interface StdLegalEnterpriseExecutedService {
    public List<StdLegalEnterpriseExecuted> findStdLegalEnterpriseExecutedByReqId(String reqId);
    public void delete(StdLegalEnterpriseExecuted stdLegalEnterpriseExecuted);
}
