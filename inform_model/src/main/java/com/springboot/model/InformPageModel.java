package com.springboot.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author 刘宏飞
 * @Date 2020/12/2 17:26
 * @Version 1.0
 */
@Data
public class InformPageModel {

    //举报线索ID
    private Long id;
    //线索编号
    private String clueNumber;
    //被举报对象名称
    private String informName;
    //举报时间
    private LocalDateTime informTime;
    //核查状态
    private String checkStatus;
    //预期
    private Boolean overdue;
    //线索属实性审核
    private String verification;
    //奖励情况
    private String rewardContent;
    //分派状态
    private String assignment;
    //分派区域ID
    private Long areaId;
    //分派区域
    private String areaName;
    //逾期时间
    private LocalDate expireTime;
    //操作列表
    private List<String> operations;
}
