package com.springboot.domain.risk;

import com.springboot.config.SpringContextUtil;
import com.springboot.exception.ServiceException;
import com.springboot.utils.ServerCacheUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class QuotaTask implements Callable<QuotaValue> {

    private String reqId;
    private Quota quota;

    public QuotaTask(String reqId, Quota quota){
        this.reqId = reqId;
        this.quota = quota;
    }

    @Override
    public QuotaValue call() throws Exception {
        QuotaExecutor quotaExecutor = getQuotaExecutor();
        Map map = quotaExecutor.execQuota(reqId, quota);
        if(ObjectUtils.isEmpty(map)){
            throw new ServiceException("在标准表中找不到记录， reqId = " + reqId);
        }
        QuotaValue quotaValue = getQuotaValue(map);
        return quotaValue;
    }

    /**
     * 得到指标执行器
     * @return
     */
    private QuotaExecutor getQuotaExecutor(){
        QuotaExecutor quotaExecutor = null;
        if("SQL".equals(quota.getCulType())){
            quotaExecutor = SpringContextUtil.getBean("quotaSqlExector");
        }else if("JAVA".equals(quota.getCulType())){
            quotaExecutor = SpringContextUtil.getBean("quotaJavaExector");
        }
        if(quotaExecutor == null){
            throw new ServiceException("找不到执行组件！");
        }
        return quotaExecutor;
    }

    /**
     * 得到指标值
     * @param map
     * @return
     */
    private QuotaValue getQuotaValue(Map map){
        QuotaValue quotaValue = new QuotaValue();
        quotaValue.setQuotaId(quota.getId());
        quotaValue.setReqId(reqId);
        quotaValue.setQuotaValue(map.get("keyvalue") != null ? map.get("keyvalue").toString() : null);
        //计算理想区间
        if(quota.getQuotaCode() != null){
            QuotaGrand quotaGrand = getQuotaGrand(quotaValue.getQuotaValue());
            quotaValue.setIdealInterval(quotaGrand.getIdealInterval());
            quotaValue.setMinusPoints(quotaGrand.getMinusPoints());
        }
        return quotaValue;
    }

    /**
     * 得到指标分档
     * @return
     */
    private QuotaGrand getQuotaGrand(String val) {
        //通过指标码得到分档列表
        List<QuotaGrand> quotaGrandList = ServerCacheUtils.getQuotaGrandListByCode(quota.getQuotaCode());
        //通过指标值计算出属于那个分档并返回

        if(val == null) {
            QuotaGrand quotaGrand = quotaGrandList.stream().filter(item -> {
                return ObjectUtils.isEmpty(item.getGrandUpper()) && ObjectUtils.isEmpty(item.getGrandLower());
            }).findFirst().get();
            if(quotaGrand == null){
                throw new ServiceException("没找到分档值，code = " + quota.getQuotaCode()+" value = " + val);
            }
           return quotaGrand;
        }

        for(QuotaGrand quotaGrand : quotaGrandList){
            //todo 排除值分档 值分档不用转成BigDecimal

            BigDecimal decimalVal = new BigDecimal(val);
            if(!ObjectUtils.isEmpty(quotaGrand.getGrandUpper()) && !ObjectUtils.isEmpty(quotaGrand.getGrandLower())){
                if(decimalVal.compareTo(quotaGrand.getGrandUpper()) <= 0 &&
                        decimalVal.compareTo(quotaGrand.getGrandLower()) > 0) {
                    return quotaGrand;
                }
            } else if(!ObjectUtils.isEmpty(quotaGrand.getGrandUpper())) {
                if(decimalVal.compareTo(quotaGrand.getGrandUpper()) <= 0) {
                    return quotaGrand;
                }
            } else if(!ObjectUtils.isEmpty(quotaGrand.getGrandLower())){
                if(decimalVal.compareTo(quotaGrand.getGrandLower()) > 0) {
                    return quotaGrand;
                }
            }
        }

        throw new ServiceException("没找到分档值，code = " + quota.getQuotaCode()+" value = " + val);
    }
}
