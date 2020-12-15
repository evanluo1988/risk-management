package com.springboot.domain.risk.executor;

import com.springboot.domain.risk.Quota;
import com.springboot.domain.risk.executor.QuotaExecutor;
import com.springboot.mapper.ExeSqlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("quotaSqlExector")
public class QuotaSqlExector implements QuotaExecutor {
    @Autowired
    private ExeSqlMapper exeSqlMapper;

    @Override
    public Map execQuota(String reqId, Quota quota) {
        String quotaRule = quota.getQuotaRule();
        String exeQuotaRule = quotaRule + " and req_id = '" + reqId + "'";
        return exeSqlMapper.exeQuotaSql(exeQuotaRule);
    }
}
