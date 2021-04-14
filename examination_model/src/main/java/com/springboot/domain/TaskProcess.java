package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.enums.ProcessTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author lhf
 * @date 2021/4/14 2:46 下午
 **/
@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@TableName("task_processes")
public class TaskProcess extends BaseDomain{

    private Long taskId;
    private ProcessTypeEnum processType;
    private Long processUserId;
    private String processMessage;

}
