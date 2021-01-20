package com.springboot.vo;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/20 17:36
 */
@Data
public class TaskCheckInfoVo {
    private Long id;
    /**
     * 核查状态
     */
    private String checkStatus;
    /**
     * 相关线索
     */
    private String relatedClues;
}
