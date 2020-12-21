package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author 刘宏飞
 * @Date 2020/12/18 13:25
 * @Version 1.0
 */
@Data
@TableName("std_legal_ent_unexecuted_temp")
public class StdLegalEntUnexecutedTemp extends StdLegalEntUnexecuted {
    /**
     * 风险等级
     */
    private String caseRiskLevel;
    private Long stdLegalEntUnexecutedId;
}
