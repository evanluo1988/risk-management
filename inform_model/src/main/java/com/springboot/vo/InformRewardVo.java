package com.springboot.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/20 16:23
 */
@Data
public class InformRewardVo {
    private Long id;
    /**
     * 奖励情况
     */
    private String rewardContent;

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
