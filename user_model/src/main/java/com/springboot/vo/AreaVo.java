package com.springboot.vo;

import lombok.Data;

/**
 * @Author 刘宏飞
 * @Date 2020/11/26 17:20
 * @Version 1.0
 */
@Data
public class AreaVo {
    private Long id;
    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 区域描述
     */
    private String areaDescribe;

    /**
     * 区域类型：R为区，S为街道办
     */
    private String type;

    /**
     * 父级区域ID
     */
    private Long parentId;
}
