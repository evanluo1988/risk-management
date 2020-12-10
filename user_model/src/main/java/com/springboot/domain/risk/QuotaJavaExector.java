package com.springboot.domain.risk;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service("quotaJavaExector")
public class QuotaJavaExector implements QuotaExecutor {
    @Override
    public Map execQuota(String reqId, Quota quota) {
        //通过 quotaRule 得到执行指标组件类，之后执行组件
        return null;
    }
}
