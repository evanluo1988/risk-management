package com.springboot.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/28 10:28
 */
@Data
public class TaskRefundOutputVo {
    private String createBy;
    private String reason;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date createTime;
}
