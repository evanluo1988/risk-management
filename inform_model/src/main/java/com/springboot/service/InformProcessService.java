package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.InformProcess;
import com.springboot.enums.ProcessTypeEnum;
import com.springboot.vo.InformProcessVo;

import java.util.List;

/**
 * @author lhf
 * @date 2021/4/1 4:51 下午
 **/
public interface InformProcessService extends IService<InformProcess> {
    /**
     * 添加处理进展
     * @param informId  举报id
     * @param processTypeEnum   处理类型
     * @param opMessage 操作意见
     * @param opUserId    操作用户id
     */
    InformProcess addProcess(Long informId, ProcessTypeEnum processTypeEnum, String opMessage, Long opUserId);

    /**
     * 查询处理过程
     * @param id
     * @return
     */
    List<InformProcessVo> listProcess(Long id);
}
