package com.springboot.vo.risk;

import lombok.Data;

@Data
public class FiveDRaderItemVo {
    private Long quotaId;
    private String quotaCode;
    private String quotaName;
    private String quotaValue;
    private Long firstLevelId;
    private Integer minusPoints;
}
