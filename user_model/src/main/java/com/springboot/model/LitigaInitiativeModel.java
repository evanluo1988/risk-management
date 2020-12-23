package com.springboot.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class LitigaInitiativeModel {
    private Long id;
    private String reqId;
    private String plaintiff;
    private String phase;
}
