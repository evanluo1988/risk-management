package com.springboot.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class InformRefundVo {
    public interface RefundGroup{}

    /**
     * 退回原因
     */
    @NotBlank(message = "退回原因必填",groups = InformRefundVo.RefundGroup.class)
    private String refundReason;
}
