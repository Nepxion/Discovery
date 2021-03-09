package com.nepxion.discovery.plugin.strategy.hystrix.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Hao Huang
 * @version 1.0
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.strategy.wrapper.StrategyCallableWrapper;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;
import com.netflix.hystrix.strategy.properties.HystrixProperty;

// 使用线程隔离模式时，无法获取ThreadLocal中信息，自定义并发策略解决
public class HystrixContextConcurrencyStrategy extends HystrixConcurrencyStrategy {
    @Autowired
    private StrategyCallableWrapper strategyCallableWrapper;

    private HystrixConcurrencyStrategy hystrixConcurrencyStrategy;

    public HystrixContextConcurrencyStrategy() {
        // HystrixPlugins只能注册一次策略，保留原对象
        this.hystrixConcurrencyStrategy = HystrixPlugins.getInstance().getConcurrencyStrategy();

        // Keeps references of existing Hystrix plugins.
        HystrixCommandExecutionHook commandExecutionHook = HystrixPlugins.getInstance().getCommandExecutionHook();
        HystrixEventNotifier eventNotifier = HystrixPlugins.getInstance().getEventNotifier();
        HystrixMetricsPublisher metricsPublisher = HystrixPlugins.getInstance().getMetricsPublisher();
        HystrixPropertiesStrategy propertiesStrategy = HystrixPlugins.getInstance().getPropertiesStrategy();

        HystrixPlugins.reset();

        // Registers existing plugins excepts the Concurrent Strategy plugin.
        HystrixPlugins.getInstance().registerConcurrencyStrategy(this);
        HystrixPlugins.getInstance().registerCommandExecutionHook(commandExecutionHook);
        HystrixPlugins.getInstance().registerEventNotifier(eventNotifier);
        HystrixPlugins.getInstance().registerMetricsPublisher(metricsPublisher);
        HystrixPlugins.getInstance().registerPropertiesStrategy(propertiesStrategy);
    }

    @Override
    public BlockingQueue<Runnable> getBlockingQueue(int maxQueueSize) {
        return hystrixConcurrencyStrategy.getBlockingQueue(maxQueueSize);
    }

    @Override
    public <T> HystrixRequestVariable<T> getRequestVariable(HystrixRequestVariableLifecycle<T> rv) {
        return hystrixConcurrencyStrategy.getRequestVariable(rv);
    }

    @Override
    public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey, HystrixProperty<Integer> corePoolSize, HystrixProperty<Integer> maximumPoolSize, HystrixProperty<Integer> keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        return hystrixConcurrencyStrategy.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey, HystrixThreadPoolProperties threadPoolProperties) {
        return hystrixConcurrencyStrategy.getThreadPool(threadPoolKey, threadPoolProperties);
    }

    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        Callable<T> originCallable = hystrixConcurrencyStrategy.wrapCallable(callable);

        return strategyCallableWrapper.wrapCallable(originCallable);
    }
}