package com.springboot.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class QuotaModel implements Serializable {
    private Long id;
    private String reqId;
    /**
     * 指标码
     */
    private String quotaCode;
    /**
     * 分档码
     */
    private String grandCode;
    /**
     * 指标名称
     */
    private String quotaName;
    /**
     * 指标维度模块ID
     */
    private Long modelId;
    /**
     * 指标一级维度ID
     */
    private Long firstLevelId;
    /**
     * 指标二级维度ID
     */
    private Long secondLevelId;
    /**
     * 时段
     */
    private String timeInterval;
    /**
     * 指标值
     */
    private String quotaValue;
    /**
     *
     */
    private Integer minusPoints;
    /**
     * 是否在理想区间
     */
    private String idealInterval;

}
