package com.springboot.model;

import com.springboot.domain.StdLegalEnterpriseExecuted;
import lombok.Data;

@Data
public class StdLegalEnterpriseExecutedModel extends StdLegalEnterpriseExecuted {
    private String riskLevel;
}
