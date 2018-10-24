package com.nepxion.discovery.plugin.strategy.zuul.configuration;

import com.netflix.hystrix.Hystrix;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@ConditionalOnClass({ Hystrix.class})
public class RequestContextHystrixAutoConfiguration {
    @Autowired(required = false)
    private HystrixConcurrencyStrategy existingConcurrencyStrategy;

    @PostConstruct
    public void init() {
        // Keeps references of existing Hystrix plugins.
        HystrixEventNotifier eventNotifier = HystrixPlugins.getInstance()
                .getEventNotifier();
        HystrixMetricsPublisher metricsPublisher = HystrixPlugins.getInstance()
                .getMetricsPublisher();
        HystrixPropertiesStrategy propertiesStrategy = HystrixPlugins.getInstance()
                .getPropertiesStrategy();
        HystrixCommandExecutionHook commandExecutionHook = HystrixPlugins.getInstance()
                .getCommandExecutionHook();

        HystrixPlugins.reset();

        // Registers existing plugins excepts the Concurrent Strategy plugin.
        HystrixPlugins.getInstance().registerConcurrencyStrategy(
                new RequestContextHystrixConcurrencyStrategy(existingConcurrencyStrategy));
        HystrixPlugins.getInstance().registerEventNotifier(eventNotifier);
        HystrixPlugins.getInstance().registerMetricsPublisher(metricsPublisher);
        HystrixPlugins.getInstance().registerPropertiesStrategy(propertiesStrategy);
        HystrixPlugins.getInstance().registerCommandExecutionHook(commandExecutionHook);
    }
}
