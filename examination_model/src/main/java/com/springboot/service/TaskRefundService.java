package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.TaskRefund;
import com.springboot.vo.TaskRefundOutputVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-12-08
 */
public interface TaskRefundService extends IService<TaskRefund> {

    void refund(Long taskCheckId, String refundReason);

    List<TaskRefundOutputVo> listRefundByTaskCheckId(Long taskCheckId);
}
