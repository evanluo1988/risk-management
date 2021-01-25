package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.springboot.vo.risk.LitigaCaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author 刘宏飞
 * @Date 2020/12/18 13:22
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("std_legal_data_structured_temp")
public class StdLegalDataStructuredTemp extends StdLegalDataStructured {
    /**
     * 风险等级
     */
    @TableField(value = "caserisklevel")
    private String caseRiskLevel;
    private Long stdLegalDataStructuredId;

    public LitigaCaseVo toLitigaCaseVo(StdLegalCasemedianTemp stdLegalCasemedianTemp) {
        LitigaCaseVo litigaCaseVo = LitigaCaseVo.builder()
                .caseCode(this.getCaseNo())
                .docClass(calcDocClass(this.getDocuClass()))
                .caseReason(this.getCaseReason())
                .lawStatus(this.getLawstatus())
                .intervalYear(calcIntervalYear(stdLegalCasemedianTemp.getIntervalYear()))
                .payment(getPayment(stdLegalCasemedianTemp))
                .courtLevel(stdLegalCasemedianTemp.getCourtLevel())
                .sentenceBrief(calcSentenceBrief(stdLegalCasemedianTemp.getSentenceBrief()))
                .sentenceEffect(calcSentenceEffect(stdLegalCasemedianTemp.getSentenceEffect()))
                .build();
        return litigaCaseVo;
    }

    /**
     * 向下取整
     * @param intervalYear
     * @return
     */
    private String calcIntervalYear(String intervalYear) {
        if(intervalYear != null && intervalYear.indexOf(".") > 0) {
            return intervalYear.substring(0, intervalYear.indexOf("."));
        }
        return intervalYear;
    }

    private String getPayment(StdLegalCasemedianTemp stdLegalCasemedianTemp) {
        String payment = null;
        if (Objects.nonNull(stdLegalCasemedianTemp)){
            payment = stdLegalCasemedianTemp.getPayment();
        }
        //Objects.isNull(stdLegalCasemedianTemp) ? null : StringUtils.isNotBlank(stdLegalCasemedianTemp.getPayment()) ? stdLegalCasemedianTemp.getPayment() : String.valueOf(Objects.isNull(this.getTargetAmount())?null:this.getTargetAmount().setScale(4, BigDecimal.ROUND_HALF_UP))
        return payment;
    }

    /**
     * 1 案件结果对客户不利
     * 0 案件结果对客户有利
     * 99 无法确定
     *
     * @param sentenceEffectCode
     * @return
     */
    private String calcSentenceEffect(String sentenceEffectCode) {
        String sentenceEffect = null;
        Map<String, String> sentenceEffectMap = Maps.newHashMap();
        sentenceEffectMap.put("1", "案件结果对客户不利");
        sentenceEffectMap.put("0", "案件结果对客户有利");
        sentenceEffectMap.put("99", "无法确定");
        if (StringUtils.isNotBlank(sentenceEffectCode)) {
            sentenceEffect = sentenceEffectMap.get(sentenceEffectCode);
        }
        return sentenceEffect;
    }

    /**
     * 01 裁定不予立案
     * 02 驳回原告起诉
     * 03 同意原告撤诉
     * 04 裁定同意或执行原告的保全申请
     * 05 裁定驳回原告的保全申请
     * 06 裁定同意被告的管辖权异议
     * 07 裁定驳回被告的管辖权异议
     * 08 双方达成调解协议
     * 09 判决客户履行给付
     * 10 判决客户受领给付
     * 11 判决客户不受领给付
     * 12 判决客户不履行给付
     * 13 判决客户承担连带责任
     * 14 判决客户不承担连带责任
     * 15 判决客户无罪
     * 16 裁定准予行政处罚
     * 17 裁定本案中止诉讼或执行
     * 18 裁定本案终结诉讼或执行
     * 19 驳回原告的上诉/申诉/复议/再审申请并维持原裁判
     * 20 裁定撤销原裁判并对本案提审/指令再审/发回重审
     * 21 裁定撤销原裁判并无其他裁判
     * 22 裁定同意撤回被告的管辖权异议
     * 23 判决驳回原告的全部诉讼请求
     * 24 判决支持原告的全部诉讼请求
     * 25 判决驳回被告的反诉请求
     * 26 判决支持原告的部分诉讼请求且提及客户相关义务
     * 27 判决支持原告的部分诉讼请求且未提及客户相关义务
     * 99 其他结果
     *
     * @param sentenceBriefCode
     * @return
     */
    private String calcSentenceBrief(String sentenceBriefCode) {
        String sentenceBrief = null;
        Map<String, String> sentenceBriefMap = Maps.newHashMap();
        sentenceBriefMap.put("01", "裁定不予立案");
        sentenceBriefMap.put("02", "驳回原告起诉");
        sentenceBriefMap.put("03", "同意原告撤诉");
        sentenceBriefMap.put("04", "裁定同意或执行原告的保全申请");
        sentenceBriefMap.put("05", "裁定驳回原告的保全申请");
        sentenceBriefMap.put("06", "裁定同意被告的管辖权异议");
        sentenceBriefMap.put("07", "裁定驳回被告的管辖权异议");
        sentenceBriefMap.put("08", "双方达成调解协议");
        sentenceBriefMap.put("09", "判决客户履行给付");
        sentenceBriefMap.put("10", "判决客户受领给付");
        sentenceBriefMap.put("11", "判决客户不受领给付");
        sentenceBriefMap.put("12", "判决客户不履行给付");
        sentenceBriefMap.put("13", "判决客户承担连带责任");
        sentenceBriefMap.put("14", "判决客户不承担连带责任");
        sentenceBriefMap.put("15", "判决客户无罪");
        sentenceBriefMap.put("16", "裁定准予行政处罚");
        sentenceBriefMap.put("17", "裁定本案中止诉讼或执行");
        sentenceBriefMap.put("18", "裁定本案终结诉讼或执行");
        sentenceBriefMap.put("19", "驳回原告的上诉/申诉/复议/再审申请并维持原裁判");
        sentenceBriefMap.put("20", "裁定撤销原裁判并对本案提审/指令再审/发回重审");
        sentenceBriefMap.put("21", "裁定撤销原裁判并无其他裁判");
        sentenceBriefMap.put("22", "裁定同意撤回被告的管辖权异议");
        sentenceBriefMap.put("23", "判决驳回原告的全部诉讼请求");
        sentenceBriefMap.put("24", "判决支持原告的全部诉讼请求");
        sentenceBriefMap.put("25", "判决驳回被告的反诉请求");
        sentenceBriefMap.put("26", "判决支持原告的部分诉讼请求且提及客户相关义务");
        sentenceBriefMap.put("27", "判决支持原告的部分诉讼请求且未提及客户相关义务");
        sentenceBriefMap.put("99", "其他结果");

        if (StringUtils.isNotBlank(sentenceBriefCode)) {
            sentenceBrief = sentenceBriefMap.get(sentenceBriefCode);
        }
        return sentenceBrief;
    }

    /**
     * if STD_LEGAL_DATA_STRUCTURED.案件性质/案件类型【DOCUCLASS】='1', return '刑事案件'
     * else  if STD_LEGAL_DATA_STRUCTURED.案件性质/案件类型【DOCUCLASS】='3'，return '行政案件'
     * else if  STD_LEGAL_DATA_STRUCTURED.案件性质/案件类型【DOCUCLASS】='7',  return '执行案件'
     * else if  STD_LEGAL_DATA_STRUCTURED.案件性质/案件类型【DOCUCLASS】in ('2', '4', '5', '9', '10', '11', '12', '14', '15' ), return '民事案件'
     * else return '其他'
     *
     * @param docClass
     * @return
     */
    private String calcDocClass(String docClass) {
        List<String> docs = Lists.newArrayList("2", "4", "5", "9", "10", "11", "12", "14", "15");
        String result = "其他";
        if ("1".equals(docClass)) {
            result = "刑事案件";
        } else if ("3".equals(docClass)) {
            result = "行政案件";
        } else if ("7".equals(docClass)) {
            result = "执行案件";
        } else if (docs.contains(docClass)) {
            result = "民事案件";
        }

        return result;
    }
}
