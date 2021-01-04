package com.springboot.domain.risk.executor;

import com.springboot.domain.risk.Quota;

import java.util.Map;

public interface QuotaExecutor {
    public Map execQuota(String reqId, Quota quota);
}
