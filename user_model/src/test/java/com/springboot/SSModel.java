package com.springboot;

import com.springboot.domain.risk.LegalWySsjghsj;
import lombok.Data;

import java.util.List;

@Data
public class SSModel {
    private String code;
    private String msg;
    private List<LegalWySsjghsj> data;
}
