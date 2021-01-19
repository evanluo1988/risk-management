package com.springboot.vo;

import lombok.Data;

/**
 * 代办事项
 * @author lhf
 * @version 1.0
 * @date 2021/1/19 16:39
 */
@Data
public class PendingListVo {
    /**
     * 举报逾期数量
     */
    private Integer informOverdueNum;
    /**
     * 举报待核查数量
     */
    private Integer informToCheckNum;
    /**
     * 任务逾期数量
     */
    private Integer taskOverdueNum;
    /**
     * 任务待核查数量
     */
    private Integer taskToCheckNum;
}
