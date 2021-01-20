package com.springboot.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TaskPageVo {
    public interface DispatcherGroup{ }
    public interface RefundGroup{}
    /**
     * 区域ID
     */
    @NotNull(message = "区域不能为空",groups = DispatcherGroup.class)
    private Long areaId;
    @NotBlank(message = "退回原因必填",groups = RefundGroup.class)
    private String refundReason;

}
