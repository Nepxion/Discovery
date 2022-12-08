package com.nepxion.discovery.common.thread;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DiscoveryNamedThreadFactory implements ThreadFactory {
    private AtomicInteger count = new AtomicInteger(1);
    private ThreadGroup group;
    private String namePrefix;
    private boolean daemon;

    public DiscoveryNamedThreadFactory(String namePrefix) {
        this(namePrefix, false);
    }

    public DiscoveryNamedThreadFactory(String namePrefix, boolean daemon) {
        SecurityManager securityManager = System.getSecurityManager();
        this.group = securityManager != null ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.namePrefix = namePrefix;
        this.daemon = daemon;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(group, runnable, namePrefix + "-thread-" + count.getAndIncrement(), 0);
        thread.setDaemon(daemon);

        return thread;
    }
}