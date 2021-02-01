package com.springboot.executor;

import com.google.common.collect.Lists;
import com.springboot.domain.Quota;
import com.springboot.domain.QuotaGrand;
import com.springboot.domain.QuotaValue;
import com.springboot.exception.ServiceException;
import com.springboot.utils.DetectCacheUtils;
import com.springboot.utils.SpringContextUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

//todo 将来需要去掉Data，这个是为了测试
@Data
@Slf4j
public class QuotaTask implements Callable<QuotaValue> {

    private String reqId;
    private Quota quota;

    public QuotaTask(String reqId, Quota quota) {
        this.reqId = reqId;
        this.quota = quota;
    }

    @Override
    public QuotaValue call() {
        QuotaExecutor quotaExecutor = getQuotaExecutor();
        Map map = quotaExecutor.execQuota(reqId, quota);
        QuotaValue quotaValue = getQuotaValue(map);
        return quotaValue;
    }

    /**
     * 得到指标执行器
     *
     * @return
     */
    private QuotaExecutor getQuotaExecutor() {
        QuotaExecutor quotaExecutor = null;
        if ("SQL".equals(quota.getCulType())) {
            quotaExecutor = SpringContextUtil.getBean("quotaSqlExector");
        } else if ("JAVA".equals(quota.getCulType())) {
            quotaExecutor = SpringContextUtil.getBean("quotaJavaExector");
        }
        if (quotaExecutor == null) {
            throw new ServiceException("找不到执行组件！");
        }
        return quotaExecutor;
    }

    /**
     * 得到指标值
     *
     * @param map
     * @return
     */
    private QuotaValue getQuotaValue(Map map) {
        QuotaValue quotaValue = new QuotaValue();
        quotaValue.setQuotaId(quota.getId());
        quotaValue.setReqId(reqId);
        quotaValue.setQuotaValue((map != null && map.get("keyvalue") != null) ? map.get("keyvalue").toString() : null);
        quotaValue.setQuotaSql((map != null && map.get("quota_sql") != null) ? map.get("quota_sql").toString() : null);
        //计算理想区间
        if (quota.getGrandCode() != null) {
            QuotaGrand quotaGrand = getQuotaGrand(quotaValue.getQuotaValue());
            quotaValue.setIdealInterval(quotaGrand.getIdealInterval());
            quotaValue.setMinusPoints(quotaGrand.getMinusPoints());
        }
        return quotaValue;
    }

    /**
     * 得到指标分档
     *
     * @return
     */
    public QuotaGrand getQuotaGrand(String val) {
        //通过分档码得到分档列表
        List<QuotaGrand> quotaGrandList = DetectCacheUtils.getQuotaGrandListByCode(quota.getGrandCode());
        //通过指标值计算出属于那个分档并返回

        if (val == null) {
            QuotaGrand quotaGrand = quotaGrandList.stream().filter(item -> {
                return ObjectUtils.isEmpty(item.getGrandUpper()) && ObjectUtils.isEmpty(item.getGrandLower()) && ObjectUtils.isEmpty(item.getQuotaValue());
            }).findFirst().get();
            if (quotaGrand == null) {
                throw new ServiceException("没找到分档值，code = " + quota.getGrandCode() + " value = " + val);
            }
            return quotaGrand;
        }

        //可以equals判断的指标
        final List<String> eqQuotaGrandCodes = Lists.newArrayList(
                "MAX_QUALIFICATION_SHAREHOLDER"
                );

        if (eqQuotaGrandCodes.contains(quota.getGrandCode())) {
            return quotaGrandList.stream()
                    .filter(quotaGrand -> quotaGrand.getQuotaValue().equalsIgnoreCase(val))
                    .findFirst()
                    .get();
        }

        // else逻辑
        final List<String> elQuotaGrandCodes = Lists.newArrayList(
                "LITIGA_FREQUENCY",
                "TENDENCY_JUDG_RESULT",
                "LITIGA_INITIATIVE");

        if (elQuotaGrandCodes.contains(quota.getGrandCode())) {
            return quotaGrandList.stream()
                    .filter(quotaGrand -> Objects.nonNull(quotaGrand.getQuotaValue()))
                    .filter(quotaGrand -> quotaGrand.getQuotaValue().equalsIgnoreCase(val))
                    .findFirst()
                    .orElse(quotaGrandList
                            .stream()
                            .filter(quotaGrand -> Objects.nonNull(quotaGrand.getQuotaValue()))
                            .filter(quotaGrand -> quotaGrand.getQuotaValue().equalsIgnoreCase("else"))
                            .findFirst()
                            .get());
        }

        //需要contains判断的指标
        final String containsGrandCode = "ENT_ABNORMAL_STATE";
        if (containsGrandCode.equals(quota.getGrandCode())) {
            //获取value编码
            final String code[] = new String[1];
            if (val.contains("在营")||val.contains("存续")||val.contains("存活")||val.contains("开业")|| val.contains("正常")||val.contains("在册")){
                code[0] = "01";
            }else if (val.contains("吊销")){
                code[0] = "02";
            }else if (val.contains("注销") || val.contains("清算") || val.contains("停业")){
                code[0] = "03";
            }else if (val.contains("迁出")){
                code[0] = "04";
            }else {
                code[0] = "99";
            }

            //根据编码判断分档 contains
            return quotaGrandList.stream()
                    .filter(quotaGrand -> !StringUtils.isEmpty(quotaGrand.getQuotaValue()))
                    .filter(quotaGrand -> quotaGrand.getQuotaValue().contains(code[0]))
                    .findFirst()
                    .orElse(quotaGrandList.stream()
                            .filter(quotaGrand -> StringUtils.isEmpty(quotaGrand.getQuotaValue()))
                            .findFirst()
                            .get());
        }

        //分档区间判断的指标
        for (QuotaGrand quotaGrand : quotaGrandList) {
            BigDecimal decimalVal = new BigDecimal(val);
            if (!ObjectUtils.isEmpty(quotaGrand.getGrandUpper()) && !ObjectUtils.isEmpty(quotaGrand.getGrandLower())) {
                if (decimalVal.compareTo(quotaGrand.getGrandUpper()) <= 0 &&
                        decimalVal.compareTo(quotaGrand.getGrandLower()) > 0) {
                    return quotaGrand;
                }
            } else if (!ObjectUtils.isEmpty(quotaGrand.getGrandUpper())) {
                if (decimalVal.compareTo(quotaGrand.getGrandUpper()) <= 0) {
                    return quotaGrand;
                }
            } else if (!ObjectUtils.isEmpty(quotaGrand.getGrandLower())) {
                if (decimalVal.compareTo(quotaGrand.getGrandLower()) > 0) {
                    return quotaGrand;
                }
            }
        }

        log.info("没找到分档值，code = " + quota.getQuotaCode() + " value = " + val);
        return new QuotaGrand();
    }
}
