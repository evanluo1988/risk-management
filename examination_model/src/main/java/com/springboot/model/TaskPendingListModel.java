package com.springboot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/19 17:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
