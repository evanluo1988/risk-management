package com.springboot.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.enums.ProcessTypeEnum;
import lombok.Data;

import java.util.Date;

/**
 * @author lhf
 * @date 2021/4/14 3:27 下午
 **/
@Data
public class TaskProcessVo {
    private Long id;
    private ProcessTypeEnum processType;
    private Long taskId;
    private String processMessage;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
