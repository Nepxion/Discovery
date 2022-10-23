package com.nepxion.discovery.plugin.strategy.zuul.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.GatewayType;
import com.nepxion.discovery.common.entity.ServiceType;
import com.nepxion.discovery.common.util.EnvironmentUtil;

public class ZuulStrategyEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        if (EnvironmentUtil.isStandardEnvironment(environment)) {
            System.setProperty(DiscoveryConstant.SPRING_APPLICATION_TYPE, ServiceType.GATEWAY.toString());
            System.setProperty(DiscoveryConstant.SPRING_APPLICATION_GATEWAY_TYPE, GatewayType.ZUUL.toString());
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}