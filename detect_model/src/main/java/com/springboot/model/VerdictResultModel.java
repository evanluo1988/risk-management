package com.springboot.model;

import lombok.Data;

@Data
public class VerdictResultModel {
    private Long id;
    private String reqId;
    private String ptype;
    private String caseResult;
    private String sentenceEffect;
}
