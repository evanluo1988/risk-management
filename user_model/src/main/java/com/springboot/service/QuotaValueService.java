package com.springboot.service;

import com.springboot.domain.risk.QuotaValue;
import com.springboot.model.QuotaModel;

import java.util.List;

public interface QuotaValueService {
    public void saveQuotaValues(List<QuotaValue> quotaValueList);
    public List<QuotaModel> getQuotaList(String reqId);
}
