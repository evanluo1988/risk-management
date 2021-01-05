package com.springboot.model;

import com.springboot.domain.StdLegalDataStructured;
import lombok.Data;

@Data
public class StdLegalDataStructuredModel extends StdLegalDataStructured {
    private String riskLevel;
}
