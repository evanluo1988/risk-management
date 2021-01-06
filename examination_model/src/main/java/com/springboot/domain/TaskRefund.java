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
 * @since 2020-12-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("task_refunds")
public class TaskRefund extends BaseDomain {

    private static final long serialVersionUID = 1L;

    /**
     * 举报ID
     */
    private Long taskCheckId;

    /**
     * 退回原因
     */
    private String reason;
}
