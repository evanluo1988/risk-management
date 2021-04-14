package com.springboot.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.enums.ProcessTypeEnum;
import lombok.Data;

import java.util.Date;

/**
 * @author lhf
 * @date 2021/4/14 4:53 下午
 **/
@Data
public class InformProcessVo {
    private Long id;
    private ProcessTypeEnum processType;
    private Long informId;
    private String processMessage;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
