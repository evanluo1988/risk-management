package com.springboot.model;

import com.springboot.domain.risk.StdLegalEnterpriseExecuted;
import lombok.Data;

@Data
public class StdLegalEnterpriseExecutedModel extends StdLegalEnterpriseExecuted {
    private String riskLevel;
}
