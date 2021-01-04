package com.springboot.model;

import lombok.Data;

@Data
public class LitigaInitiativeModel {
    private Long id;
    private String reqId;
    private String plaintiff;
    private String phase;
}
