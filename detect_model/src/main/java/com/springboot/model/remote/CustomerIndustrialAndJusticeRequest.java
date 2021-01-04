package com.springboot.model.remote;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 工商司法请求实体
 */
@Data
@Accessors(chain = true)
public class CustomerIndustrialAndJusticeRequest {
    private String businessID;
    private String entName;
    private String entCreditID="911112345671234567";
    private String indName="黄日林";
    private String indCertID="uTln8yoWNBcFDHgUv24IXvQTVoGyDvrYzKvAcHzbyhM=";
    private String productCode = "product_qygssf";
    private String appID;
    private String timestamp;
    private String signature;
}
