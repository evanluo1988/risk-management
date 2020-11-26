package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-11-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("areas")
public class Area extends BaseDomain {

    private static final long serialVersionUID = 1L;

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 区域描述
     */
    private String areaDiscribe;

    /**
     * 是否禁用：Y为正常，N为禁用
     */
    private String enable;

    /**
     * 区域类型：R为区，S为街道办
     */
    private String type;

    /**
     * 父级区域ID
     */
    private Long parentId;
}
