package com.nepxion.discovery.plugin.framework.decorator;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Date;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.IClientConfig;
import com.netflix.config.DynamicIntProperty;
import com.netflix.loadbalancer.ServerListUpdater;

// 拷贝自Netflix源码，在某些场景下在某些场景下会出现内存泄漏问题，当然只是Warn级别
// 原来的方案：通过钩子关闭线程池，这种实现方案不好
// 改进的方案：在Spring销毁上下文的时候，关闭线程池，通过ContextClosedEvent触发线程池关闭，可以避免内存泄漏问题
/**
 * A default strategy for the dynamic server list updater to update.
 * (refactored and moved here from {@link com.netflix.loadbalancer.DynamicServerListLoadBalancer})
 *
 * @author David Liu
 */
public class PollingServerListUpdaterDecorator implements ServerListUpdater, ApplicationListener<ContextClosedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(PollingServerListUpdaterDecorator.class);

    private static long LISTOFSERVERS_CACHE_UPDATE_DELAY = 1000; // msecs;
    private static int LISTOFSERVERS_CACHE_REPEAT_INTERVAL = 30 * 1000; // msecs;

    private static class LazyHolder {
        private final static String CORE_THREAD = "DynamicServerListLoadBalancer.ThreadPoolSize";
        private final static DynamicIntProperty poolSizeProp = new DynamicIntProperty(CORE_THREAD, 2);
        private static Thread _shutdownThread;

        static ScheduledThreadPoolExecutor _serverListRefreshExecutor = null;

        static {
            int coreSize = poolSizeProp.get();
            ThreadFactory factory = (new ThreadFactoryBuilder())
                    .setNameFormat("PollingServerListUpdater-%d")
                    .setDaemon(true)
                    .build();
            _serverListRefreshExecutor = new ScheduledThreadPoolExecutor(coreSize, factory);
            poolSizeProp.addCallback(new Runnable() {
                @Override
                public void run() {
                    _serverListRefreshExecutor.setCorePoolSize(poolSizeProp.get());
                }

            });
            _shutdownThread = new Thread(new Runnable() {
                public void run() {
                    logger.info("Shutting down the Executor Pool for PollingServerListUpdater");
                    shutdownExecutorPool();
                }
            });
            Runtime.getRuntime().addShutdownHook(_shutdownThread);
        }

        private static void shutdownExecutorPool() {
            if (_serverListRefreshExecutor != null) {
                _serverListRefreshExecutor.shutdown();

                if (_shutdownThread != null) {
                    try {
                        Runtime.getRuntime().removeShutdownHook(_shutdownThread);
                    } catch (IllegalStateException ise) { // NOPMD
                        // this can happen if we're in the middle of a real
                        // shutdown,
                        // and that's 'ok'
                    }
                }

            }
        }
    }

    private static ScheduledThreadPoolExecutor getRefreshExecutor() {
        return LazyHolder._serverListRefreshExecutor;
    }


    private final AtomicBoolean isActive = new AtomicBoolean(false);
    private volatile long lastUpdated = System.currentTimeMillis();
    private final long initialDelayMs;
    private final long refreshIntervalMs;

    private volatile ScheduledFuture<?> scheduledFuture;

    public PollingServerListUpdaterDecorator() {
        this(LISTOFSERVERS_CACHE_UPDATE_DELAY, LISTOFSERVERS_CACHE_REPEAT_INTERVAL);
    }

    public PollingServerListUpdaterDecorator(IClientConfig clientConfig) {
        this(LISTOFSERVERS_CACHE_UPDATE_DELAY, getRefreshIntervalMs(clientConfig));
    }

    public PollingServerListUpdaterDecorator(final long initialDelayMs, final long refreshIntervalMs) {
        this.initialDelayMs = initialDelayMs;
        this.refreshIntervalMs = refreshIntervalMs;
    }

    @Override
    public synchronized void start(final UpdateAction updateAction) {
        if (isActive.compareAndSet(false, true)) {
            final Runnable wrapperRunnable = new Runnable() {
                @Override
                public void run() {
                    if (!isActive.get()) {
                        if (scheduledFuture != null) {
                            scheduledFuture.cancel(true);
                        }
                        return;
                    }
                    try {
                        updateAction.doUpdate();
                        lastUpdated = System.currentTimeMillis();
                    } catch (Exception e) {
                        logger.warn("Failed one update cycle", e);
                    }
                }
            };

            scheduledFuture = getRefreshExecutor().scheduleWithFixedDelay(
                    wrapperRunnable,
                    initialDelayMs,
                    refreshIntervalMs,
                    TimeUnit.MILLISECONDS
            );
        } else {
            logger.info("Already active, no-op");
        }
    }

    @Override
    public synchronized void stop() {
        if (isActive.compareAndSet(true, false)) {
            if (scheduledFuture != null) {
                scheduledFuture.cancel(true);
            }
        } else {
            logger.info("Not active, no-op");
        }
    }

    @Override
    public String getLastUpdate() {
        return new Date(lastUpdated).toString();
    }

    @Override
    public long getDurationSinceLastUpdateMs() {
        return System.currentTimeMillis() - lastUpdated;
    }

    @Override
    public int getNumberMissedCycles() {
        if (!isActive.get()) {
            return 0;
        }
        return (int) ((int) (System.currentTimeMillis() - lastUpdated) / refreshIntervalMs);
    }

    @Override
    public int getCoreThreads() {
        if (isActive.get()) {
            if (getRefreshExecutor() != null) {
                return getRefreshExecutor().getCorePoolSize();
            }
        }
        return 0;
    }

    private static long getRefreshIntervalMs(IClientConfig clientConfig) {
        return (long) clientConfig.get(CommonClientConfigKey.ServerListRefreshInterval, LISTOFSERVERS_CACHE_REPEAT_INTERVAL);
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        getRefreshExecutor().shutdown();
    }
}