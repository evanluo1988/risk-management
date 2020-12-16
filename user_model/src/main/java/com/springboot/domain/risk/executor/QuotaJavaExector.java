package com.springboot.domain.risk.executor;

import com.google.common.collect.Maps;
import com.springboot.component.QuotaComponent;
import com.springboot.domain.risk.Quota;
import com.springboot.utils.SpringContextUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("quotaJavaExector")
public class QuotaJavaExector implements QuotaExecutor {
    @Override
    public Map execQuota(String reqId, Quota quota) {
        //通过 quotaRule 得到执行指标组件类，之后执行组件
        String componentId = quota.getQuotaRule();
        QuotaComponent quotaComponent = SpringContextUtil.getBean(componentId);
        Map map = Maps.newHashMap();
        map.put("keyvalue", quotaComponent.execQuota(reqId));
        return map;
    }
}
