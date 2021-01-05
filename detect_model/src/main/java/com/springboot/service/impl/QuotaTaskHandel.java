package com.springboot.service.impl;

import com.google.common.collect.Lists;
import com.springboot.domain.QuotaValue;
import com.springboot.executor.QuotaTask;
import com.springboot.threadpool.ThreadPoolUtil;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class QuotaTaskHandel {

    public List<QuotaValue> culQuotaTasks(List<QuotaTask> quotaTaskList) {
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtil.makeServerThreadPool("QUOTA_VALUE", 4, 4);
        //执行任务
        List<QuotaValue> quotaValueList = Lists.newArrayList();
        List<Future<QuotaValue>> futureList = Lists.newArrayList();
        try {

            for(QuotaTask quotaTask : quotaTaskList){
                //quotaValueList.add(quotaTask.call());
                futureList.add(threadPoolExecutor.submit(quotaTask));
            }

            for(Future<QuotaValue> future : futureList) {
                quotaValueList.add(future.get(2000, TimeUnit.MILLISECONDS));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return quotaValueList;
    }
}
