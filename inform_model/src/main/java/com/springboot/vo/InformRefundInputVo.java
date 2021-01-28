package com.springboot.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class InformRefundInputVo {
    public interface RefundGroup{}

    /**
     * 退回原因
     */
    @NotBlank(message = "退回原因必填",groups = InformRefundInputVo.RefundGroup.class)
    private String refundReason;
}
