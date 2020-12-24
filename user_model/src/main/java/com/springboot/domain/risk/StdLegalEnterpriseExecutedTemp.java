package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.risk.StdLegalEnterpriseExecuted;
import com.springboot.vo.risk.LitigaCaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @Author 刘宏飞
 * @Date 2020/12/18 13:24
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("std_legal_enterprise_executed_temp")
public class StdLegalEnterpriseExecutedTemp extends StdLegalEnterpriseExecuted {
    /**
     * 风险等级
     */
    @TableField(value = "caserisklevel")
    private String caseRiskLevel;
    private Long stdLegalEnterpriseExecutedId;

    public LitigaCaseVo toLitigaCaseVo(){
        LitigaCaseVo litigaCaseVo = LitigaCaseVo.builder()
                .caseCode(this.getCaseCode())
                .docClass("执行案件")
                .caseReason(null)
                .lawStatus(null)
                .intervalYear(calcIntervalYear(this.getCaseCreateTime()))
                .payment(this.getExecMoney())
                .courtLevel(calcCourtLevel(this.getExecCourtName()))
                .sentenceBrief(null)
                .sentenceEffect(null)
                .build();
        return litigaCaseVo;
    }

    public static String calcCourtLevel(String execCourtName) {
        String courtLevel = null;
        if (StringUtils.isNotBlank(execCourtName)){
            if (execCourtName.contains("最高")){
                courtLevel = "最高";
            }else if (execCourtName.contains("高级")){
                courtLevel = "高级";
            }else if (execCourtName.contains("中级")){
                courtLevel = "中级";
            } else if (execCourtName.contains("区") || execCourtName.contains("市")){
                courtLevel = "基层";
            }else {
                courtLevel = "其他";
            }
        }
        return courtLevel;
    }

    private String calcIntervalYear(LocalDate caseCreateTime) {
        String intervalYear = null;
        if (Objects.nonNull(caseCreateTime)){
            LocalDate now = LocalDate.now();
            intervalYear = String.valueOf(Math.round((((double)(now.toEpochDay()-caseCreateTime.toEpochDay()))/365 )*10000)/10000);
        }
        return intervalYear;
    }
}
