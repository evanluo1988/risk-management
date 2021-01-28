package com.springboot.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/28 9:54
 */
@Data
public class InformRefundOutputVo {
    private String createBy;
    private String reason;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date createTime;
}
