package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.vo.risk.LitigaCaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @Author 刘宏飞
 * @Date 2020/12/18 13:25
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("std_legal_ent_unexecuted_temp")
public class StdLegalEntUnexecutedTemp extends StdLegalEntUnexecuted {
    /**
     * 风险等级
     */
    @TableField(value = "caserisklevel")
    private String caseRiskLevel;
    private Long stdLegalEntUnexecutedId;

    public LitigaCaseVo toLitigaCaseVo() {
        LitigaCaseVo litigaCaseVo = LitigaCaseVo.builder()
                .caseCode(this.getCaseCode())
                .docClass("执行案件")
                .caseReason(null)
                .lawStatus(null)
                .intervalYear(calcIntervalYear(this.getRegDate(),this.getPublishDate()))
                .payment(null)
                .courtLevel(StdLegalEnterpriseExecutedTemp.calcCourtLevel(this.getCourtName()))
                .sentenceBrief(null)
                .sentenceEffect(null)
                .build();
        return litigaCaseVo;
    }

    private String calcIntervalYear(LocalDate regDate, LocalDate publishDate) {
        String intervalYear = null;
        LocalDate now = LocalDate.now();
        if (Objects.nonNull(regDate)){
            String.valueOf(((now.toEpochDay()-regDate.toEpochDay())/365));
        }else if (Objects.nonNull(publishDate)){
            String.valueOf(((now.toEpochDay()-publishDate.toEpochDay())/365));
        }
        return intervalYear;
    }
}
