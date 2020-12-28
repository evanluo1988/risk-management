package com.springboot.model;

import com.springboot.domain.risk.IaAsPartentCatalog;
import com.springboot.domain.risk.IaAsPartentDetail;
import lombok.Data;

@Data
public class IaAsPartentDetailModel extends IaAsPartentDetail{
    private IaAsPartentCatalog catalogPatent;
}
