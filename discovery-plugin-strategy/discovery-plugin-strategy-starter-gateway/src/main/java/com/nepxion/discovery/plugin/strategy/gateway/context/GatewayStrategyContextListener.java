package com.nepxion.discovery.plugin.strategy.gateway.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class GatewayStrategyContextListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(GatewayStrategyContextListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 异步调用下，第一次启动在某些情况下可能存在丢失上下文的问题
        LOG.info("Initialize Gateway Strategy Context after Application started...");
        GatewayStrategyContext.getCurrentContext();
    }
}