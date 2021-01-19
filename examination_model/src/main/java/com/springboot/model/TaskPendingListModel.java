package com.springboot.model;

import lombok.Data;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/19 17:03
 */
@Data
public class TaskPendingListModel {
    /**
     * 任务逾期数量
     */
    private Integer taskOverdueNum;
    /**
     * 任务待核查数量
     */
    private Integer taskToCheckNum;
}
