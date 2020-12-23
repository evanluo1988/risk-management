package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
}
