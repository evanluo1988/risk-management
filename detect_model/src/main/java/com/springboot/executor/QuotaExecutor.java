package com.springboot.executor;

import com.springboot.domain.Quota;

import java.util.Map;

public interface QuotaExecutor {
    public Map execQuota(String reqId, Quota quota);
}
