package com.springboot.vo;

import com.springboot.domain.Enterprise;
import com.springboot.domain.Task;
import com.springboot.domain.TaskCheck;
import com.springboot.domain.TaskDisposition;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author 刘宏飞
 * @Date 2020/12/7 16:52
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class TaskDetailVo {
    private TaskInfoVo task;
    private TaskCheckInfoVo taskCheck;
    private TaskDispositionVo taskDisposition;
    private EnterpriseVo taskEnterprise;
}
