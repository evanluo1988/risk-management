package com.springboot.domain.risk;

import java.util.Map;

public interface QuotaExecutor {
    public Map execQuota(String reqId, Quota quota);
}
