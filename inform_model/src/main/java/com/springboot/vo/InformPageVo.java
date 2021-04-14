package com.springboot.vo;

import com.springboot.domain.User;
import com.springboot.model.UserInfo;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author 刘宏飞
 * @Date 2020/12/2 14:10
 * @Version 1.0
 */
@Data
public class InformPageVo {
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
    private String rewardStatus;
    //分派状态
    private String assignment;
    //分派区域ID
    private Long areaId;
    //分派区域
    private String areaName;
    //逾期时间
    private LocalDate expireTime;
    // 退回统计
    private Long refundCount;
    // 核查街道联系方式
    private List<User> areaContact;
}
