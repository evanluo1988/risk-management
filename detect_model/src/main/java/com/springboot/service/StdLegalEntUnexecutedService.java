package com.springboot.service;

import com.springboot.domain.risk.StdLegalEntUnexecuted;

import java.util.List;

public interface StdLegalEntUnexecutedService {
    public List<StdLegalEntUnexecuted> findStdLegalEntUnexecutedByReqId(String reqId);
}
