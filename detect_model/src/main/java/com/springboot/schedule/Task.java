package com.springboot.schedule;

import org.springframework.scheduling.annotation.Async;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/14 14:07
 */
public interface Task<R> {

    /**
     * 任务方法
     * @return
     */
    @Async
    R run();

    /**
     * 是否可运行状态
     * @return
     */
    boolean runable();

    /**
     * 停止运行
     */
    void stop();

    /**
     * 开始运行
     */
    void start();
}
