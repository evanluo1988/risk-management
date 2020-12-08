package com.springboot.vo;

import com.springboot.domain.Enterprise;
import com.springboot.domain.Task;
import com.springboot.domain.TaskCheck;
import com.springboot.domain.TaskDisposition;
import lombok.Data;

/**
 * @Author 刘宏飞
 * @Date 2020/12/7 16:52
 * @Version 1.0
 */
@Data
public class TaskDetailVo {
    private Task task;
    private TaskCheck taskCheck;
    private TaskDisposition taskDisposition;
    private Enterprise taskEnterprise;

    public TaskDetailVo(Task task,TaskCheck taskCheck,TaskDisposition taskDisposition,Enterprise taskEnterprise){
        this.task = task;
        this.taskCheck = taskCheck;
        this.taskDisposition = taskDisposition;
        this.taskEnterprise = taskEnterprise;
    }
}
