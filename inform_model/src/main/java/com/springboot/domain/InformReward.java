package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-11-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("inform_rewards")
public class InformReward extends BaseDomain {

    private static final long serialVersionUID = 1L;

    /**
     * 奖励情况
     */
    private String rewardStatus;

    /**
     * 奖励金额
     */
    private Long rewardAmount;

    /**
     * 奖励时间
     */
    private LocalDateTime rewardTime;

    /**
     * 举报ID
     */
    private Long informId;
}
