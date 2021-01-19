package com.springboot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/19 17:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfromPendingListModel {
    /**
     * 举报逾期数量
     */
    private Integer informOverdueNum;
    /**
     * 举报待核查数量
     */
    private Integer informToCheckNum;
}
