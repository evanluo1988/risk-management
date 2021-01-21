package com.springboot.executor;

import com.google.common.collect.Maps;
import com.springboot.domain.Quota;
import com.springboot.domain.StdEntBasic;
import com.springboot.mapper.ExeSqlMapper;
import com.springboot.service.StdEntBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
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
        HashMap map = exeSqlMapper.exeQuotaSql(exeQuotaSql);
        if(map == null) {
            map = Maps.newHashMap();
        }
        map.put("quota_sql", exeQuotaSql);
        return map;
    }

    public String replaceRule(String reqId, String quotaRule) {
        if(StringUtils.isEmpty(quotaRule)) {
            return quotaRule;
        }
        if(quotaRule.contains(ENT_NAME)){
            StdEntBasic stdEntBasic = stdEntBasicService.getStdEntBasicByReqId(reqId);
            if(stdEntBasic != null && stdEntBasic.getEntName() != null) {
                quotaRule = quotaRule.replace(ENT_NAME, stdEntBasic.getEntName());
            }
        }
        return quotaRule.replace("?", "'" +reqId + "'");
    }
}
