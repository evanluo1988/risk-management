package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.enums.ProcessTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author lhf
 * @date 2021/4/1 4:28 下午
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("inform_processes")
public class InformProcess extends BaseDomain{
    /**
     * 举报ID
     */
    private Long informId;
    /**
     * 处理类型
     */
    private ProcessTypeEnum processType;
    /**
     * 处理用户ID
     */
    private Long processUserId;
    /**
     * 附加信息
     */
    private String processMessage;
}
