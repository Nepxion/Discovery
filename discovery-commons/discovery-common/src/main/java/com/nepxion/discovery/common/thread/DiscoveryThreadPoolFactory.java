package com.nepxion.discovery.common.thread;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DiscoveryThreadPoolFactory {
    public static ExecutorService getExecutorService(String name) {
        return new ThreadPoolExecutor(2, 4, 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1), new DiscoveryNamedThreadFactory(name), new ThreadPoolExecutor.DiscardOldestPolicy());
    }
}