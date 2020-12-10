package com.springboot.service;

import com.springboot.domain.risk.QuotaValue;

import java.util.List;

public interface QuotaValueService {
    public void saveQuotaValues(List<QuotaValue> quotaValueList);
}
