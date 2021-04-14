package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.TaskProcess;
import com.springboot.enums.ProcessTypeEnum;
import com.springboot.vo.TaskProcessVo;

import java.util.List;

/**
 * @author lhf
 * @date 2021/4/14 2:48 下午
 **/
public interface TaskProcessService extends IService<TaskProcess> {
    /**
     * 添加处理过程
     * @param taskId
     * @param processTypeEnum
     * @param opMessage
     * @param opUserId
     * @return
     */
    TaskProcess addProcess(Long taskId, ProcessTypeEnum processTypeEnum, String opMessage, Long opUserId);

    /**
     * 查询处理记录
     * @param taskId
     * @return
     */
    List<TaskProcessVo> listProcess(Long taskId);
}
