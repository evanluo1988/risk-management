package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.TaskRefund;
import com.springboot.mapper.TaskRefundDao;
import com.springboot.service.TaskRefundService;
import com.springboot.utils.ConvertUtils;
import com.springboot.utils.UserAuthInfoContext;
import com.springboot.vo.TaskRefundOutputVo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-12-08
 */
@Service
public class TaskRefundServiceImpl extends ServiceImpl<TaskRefundDao, TaskRefund> implements TaskRefundService {

    @Override
    public void refund(Long taskCheckId, String refundReason) {
        TaskRefund taskRefund = new TaskRefund();
        taskRefund.setTaskCheckId(taskCheckId)
                .setReason(refundReason);
        taskRefund.setCreateBy(UserAuthInfoContext.getUserName());
        taskRefund.setCreateTime(new Date());
        save(taskRefund);
    }

    @Override
    public List<TaskRefundOutputVo> listRefundByTaskCheckId(Long taskCheckId) {
        LambdaQueryWrapper<TaskRefund> queryWrapper = new LambdaQueryWrapper<TaskRefund>()
                .eq(TaskRefund::getTaskCheckId, taskCheckId);
        List<TaskRefund> list = list(queryWrapper);
        return ConvertUtils.sourceToTarget(list,TaskRefundOutputVo.class);
    }
}
