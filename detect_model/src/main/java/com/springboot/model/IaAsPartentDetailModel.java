package com.springboot.model;

import com.springboot.domain.IaAsPartentCatalog;
import com.springboot.domain.IaAsPartentDetail;
import lombok.Data;

@Data
public class IaAsPartentDetailModel extends IaAsPartentDetail{
    private IaAsPartentCatalog catalogPatent;
}
