package com.springboot.domain.risk.executor;

import com.springboot.domain.risk.Quota;
import com.springboot.domain.risk.StdEntBasic;
import com.springboot.mapper.ExeSqlMapper;
import com.springboot.service.StdEntBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service("quotaSqlExector")
public class QuotaSqlExector implements QuotaExecutor {
    private final static String ENT_NAME = "{entname}";

    @Autowired
    private ExeSqlMapper exeSqlMapper;
    @Autowired
    private StdEntBasicService stdEntBasicService;

    @Override
    public Map execQuota(String reqId, Quota quota) {
        String exeQuotaSql = replaceRule(reqId, quota.getQuotaRule());
        return exeSqlMapper.exeQuotaSql(exeQuotaSql);
    }

    public String replaceRule(String reqId, String quotaRule) {
        if(StringUtils.isEmpty(quotaRule)) {
            return quotaRule;
        }
        if(quotaRule.contains(ENT_NAME)){
            StdEntBasic stdEntBasic = stdEntBasicService.getStdEntBasicByReqId(reqId);
            quotaRule = quotaRule.replace(ENT_NAME, stdEntBasic.getEntName());
        }

        return quotaRule.replace("?", "'" +reqId + "'");
    }
}
