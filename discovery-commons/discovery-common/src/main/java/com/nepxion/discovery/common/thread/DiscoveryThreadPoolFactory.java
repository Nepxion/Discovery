package com.nepxion.discovery.common.thread;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DiscoveryThreadPoolFactory {
    private static Map<String, ExecutorService> executorServiceMap = new ConcurrentHashMap<String, ExecutorService>();

    public static ExecutorService getExecutorService(String name) {
        ExecutorService executorService = executorServiceMap.get(name);
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(2, 4, 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1), new DiscoveryNamedThreadFactory(name), new ThreadPoolExecutor.DiscardOldestPolicy());
            executorServiceMap.put(name, executorService);
        }

        return executorService;
    }
}