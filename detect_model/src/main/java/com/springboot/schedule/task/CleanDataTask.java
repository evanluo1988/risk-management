package com.springboot.schedule.task;


import com.springboot.domain.CloudInfoTimeliness;
import com.springboot.schedule.AbsTask;
import com.springboot.schedule.Task;
import com.springboot.service.CloudInfoTimelinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 清理数据
 *
 * @author lhf
 * @version 1.0
 * @date 2021/1/14 14:10
 */
@Slf4j
@Component
public class CleanDataTask extends AbsTask {

    @Autowired
    private CloudInfoTimelinessService cloudInfoTimelinessService;
    @Value("${data.expire.month}")
    private Long dataExpireMonth;

    /**
     * 每月1日，凌晨1点
     *
     * @return
     */
    @Override
    @Scheduled(cron = "0 0 1 1 * ?")
    public Object run() {
        if (runable()) {
            log.info("清理数据定时任务开始：{}", LocalDateTime.now().toLocalTime() + "\t线程 : " + Thread.currentThread().getName());
            LocalDateTime expired = LocalDateTime.now().plusMonths(dataExpireMonth < 2 ? -2 : -dataExpireMonth);
            List<CloudInfoTimeliness> cloudInfoTimelinessList = cloudInfoTimelinessService.listExpired(expired);

            cloudInfoTimelinessList.stream().map(CloudInfoTimeliness::getReqId).forEach(cloudInfoTimelinessService::clean);
        }
        return true;
    }
}
