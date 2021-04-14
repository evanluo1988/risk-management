package com.springboot.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class InformDispatcherVo {
    public interface DispatcherGroup{}

    @NotNull(groups = InformDispatcherVo.DispatcherGroup.class,message = "区域不能为空")
    private Long areaId;
    /**
     * 操作意见
     */
    private String opMessage;
}
