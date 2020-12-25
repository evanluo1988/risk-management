package com.springboot.model.remote;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 知识产权
 */
@Data
@Accessors(chain = true)
public class CustomerIntellectualPropertyRequest {
    private String businessID;
    private String name;
    private String nameType;
    private String pageId;
    private String productCode;
    private String appID;
    private String timestamp;
    private String signature;

}
