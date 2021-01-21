package com.springboot.threadpool;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolFactroy {
    private static Map<String, ThreadPoolExecutor> threadPoolExecutorMap = Maps.newConcurrentMap();

    public static ThreadPoolExecutor getThreadPoolExecutor(String type, int coreSize, int maxSize) {
        threadPoolExecutorMap.putIfAbsent(type, ThreadPoolUtil.makeServerThreadPool(type, coreSize, maxSize));
        return threadPoolExecutorMap.get(type);
    }
}
