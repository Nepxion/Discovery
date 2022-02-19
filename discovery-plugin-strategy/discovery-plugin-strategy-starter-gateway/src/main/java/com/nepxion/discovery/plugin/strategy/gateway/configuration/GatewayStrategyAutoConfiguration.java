package com.nepxion.discovery.plugin.strategy.gateway.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Ning Zhang
 * @version 1.0
 */

import org.apache.skywalking.apm.agent.core.context.TracingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.discovery.common.apollo.proccessor.ApolloProcessor;
import com.nepxion.discovery.common.consul.proccessor.ConsulProcessor;
import com.nepxion.discovery.common.etcd.proccessor.EtcdProcessor;
import com.nepxion.discovery.common.nacos.proccessor.NacosProcessor;
import com.nepxion.discovery.common.redis.proccessor.RedisProcessor;
import com.nepxion.discovery.common.zookeeper.proccessor.ZookeeperProcessor;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.gateway.constant.GatewayStrategyConstant;
import com.nepxion.discovery.plugin.strategy.gateway.context.GatewayStrategyContextListener;
import com.nepxion.discovery.plugin.strategy.gateway.filter.DefaultGatewayStrategyClearFilter;
import com.nepxion.discovery.plugin.strategy.gateway.filter.DefaultGatewayStrategyRouteFilter;
import com.nepxion.discovery.plugin.strategy.gateway.filter.GatewayStrategyClearFilter;
import com.nepxion.discovery.plugin.strategy.gateway.filter.GatewayStrategyRouteFilter;
import com.nepxion.discovery.plugin.strategy.gateway.filter.SkyWalkingGatewayStrategyFilter;
import com.nepxion.discovery.plugin.strategy.gateway.monitor.DefaultGatewayStrategyMonitor;
import com.nepxion.discovery.plugin.strategy.gateway.monitor.GatewayStrategyMonitor;
import com.nepxion.discovery.plugin.strategy.gateway.processor.GatewayStrategyRouteApolloProcessor;
import com.nepxion.discovery.plugin.strategy.gateway.processor.GatewayStrategyRouteConsulProcessor;
import com.nepxion.discovery.plugin.strategy.gateway.processor.GatewayStrategyRouteEtcdProcessor;
import com.nepxion.discovery.plugin.strategy.gateway.processor.GatewayStrategyRouteNacosProcessor;
import com.nepxion.discovery.plugin.strategy.gateway.processor.GatewayStrategyRouteRedisProcessor;
import com.nepxion.discovery.plugin.strategy.gateway.processor.GatewayStrategyRouteZookeeperProcessor;
import com.nepxion.discovery.plugin.strategy.gateway.route.DefaultGatewayStrategyRoute;
import com.nepxion.discovery.plugin.strategy.gateway.route.GatewayStrategyRoute;
import com.nepxion.discovery.plugin.strategy.gateway.wrapper.DefaultGatewayStrategyCallableWrapper;
import com.nepxion.discovery.plugin.strategy.gateway.wrapper.GatewayStrategyCallableWrapper;

@Configuration
@AutoConfigureBefore(RibbonClientConfiguration.class)
public class GatewayStrategyAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public GatewayStrategyRouteFilter gatewayStrategyRouteFilter() {
        return new DefaultGatewayStrategyRouteFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public GatewayStrategyClearFilter gatewayStrategyClearFilter() {
        return new DefaultGatewayStrategyClearFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_MONITOR_ENABLED, matchIfMissing = false)
    public GatewayStrategyMonitor gatewayStrategyMonitor() {
        return new DefaultGatewayStrategyMonitor();
    }

    @Bean
    @ConditionalOnMissingBean
    public GatewayStrategyRoute gatewayStrategyRoute() {
        return new DefaultGatewayStrategyRoute();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_HYSTRIX_THREADLOCAL_SUPPORTED, matchIfMissing = false)
    public GatewayStrategyCallableWrapper gatewayStrategyCallableWrapper() {
        return new DefaultGatewayStrategyCallableWrapper();
    }

    @Bean
    public GatewayStrategyContextListener gatewayStrategyContextListener() {
        return new GatewayStrategyContextListener();
    }

    @ConditionalOnClass(NacosProcessor.class)
    @ConditionalOnProperty(value = GatewayStrategyConstant.SPRING_APPLICATION_STRATEGY_GATEWAY_DYNAMIC_ROUTE_ENABLED, matchIfMissing = false)
    protected static class GatewayRouteNacosConfiguration {
        @Bean
        @ConditionalOnProperty(value = "spring.cloud.gateway.discovery.locator.enabled", havingValue = "false", matchIfMissing = true)
        public NacosProcessor gatewayStrategyRouteNacosProcessor() {
            return new GatewayStrategyRouteNacosProcessor();
        }
    }

    @ConditionalOnClass(ApolloProcessor.class)
    @ConditionalOnProperty(value = GatewayStrategyConstant.SPRING_APPLICATION_STRATEGY_GATEWAY_DYNAMIC_ROUTE_ENABLED, matchIfMissing = false)
    protected static class GatewayRouteApolloConfiguration {
        @Bean
        @ConditionalOnProperty(value = "spring.cloud.gateway.discovery.locator.enabled", havingValue = "false", matchIfMissing = true)
        public ApolloProcessor gatewayStrategyRouteApolloProcessor() {
            return new GatewayStrategyRouteApolloProcessor();
        }
    }

    @ConditionalOnClass(RedisProcessor.class)
    @ConditionalOnProperty(value = GatewayStrategyConstant.SPRING_APPLICATION_STRATEGY_GATEWAY_DYNAMIC_ROUTE_ENABLED, matchIfMissing = false)
    protected static class GatewayRouteRedisConfiguration {
        @Bean
        @ConditionalOnProperty(value = "spring.cloud.gateway.discovery.locator.enabled", havingValue = "false", matchIfMissing = true)
        public RedisProcessor gatewayStrategyRouteRedisProcessor() {
            return new GatewayStrategyRouteRedisProcessor();
        }
    }

    @ConditionalOnClass(ZookeeperProcessor.class)
    @ConditionalOnProperty(value = GatewayStrategyConstant.SPRING_APPLICATION_STRATEGY_GATEWAY_DYNAMIC_ROUTE_ENABLED, matchIfMissing = false)
    protected static class GatewayRouteZookeeperConfiguration {
        @Bean
        @ConditionalOnProperty(value = "spring.cloud.gateway.discovery.locator.enabled", havingValue = "false", matchIfMissing = true)
        public ZookeeperProcessor gatewayStrategyRouteZookeeperProcessor() {
            return new GatewayStrategyRouteZookeeperProcessor();
        }
    }

    @ConditionalOnClass(ConsulProcessor.class)
    @ConditionalOnProperty(value = GatewayStrategyConstant.SPRING_APPLICATION_STRATEGY_GATEWAY_DYNAMIC_ROUTE_ENABLED, matchIfMissing = false)
    protected static class GatewayRouteConsulConfiguration {
        @Bean
        @ConditionalOnProperty(value = "spring.cloud.gateway.discovery.locator.enabled", havingValue = "false", matchIfMissing = true)
        public ConsulProcessor gatewayStrategyRouteConsulProcessor() {
            return new GatewayStrategyRouteConsulProcessor();
        }
    }

    @ConditionalOnClass(EtcdProcessor.class)
    @ConditionalOnProperty(value = GatewayStrategyConstant.SPRING_APPLICATION_STRATEGY_GATEWAY_DYNAMIC_ROUTE_ENABLED, matchIfMissing = false)
    protected static class GatewayRouteEtcdConfiguration {
        @Bean
        @ConditionalOnProperty(value = "spring.cloud.gateway.discovery.locator.enabled", havingValue = "false", matchIfMissing = true)
        public EtcdProcessor gatewayStrategyRouteEtcdProcessor() {
            return new GatewayStrategyRouteEtcdProcessor();
        }
    }

    @ConditionalOnClass(TracingContext.class)
    protected static class SkywalkingStrategyConfiguration {
        @Autowired
        private ConfigurableEnvironment environment;

        @Bean
        @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_MONITOR_ENABLED, matchIfMissing = false)
        public SkyWalkingGatewayStrategyFilter skyWalkingGatewayStrategyFilter() {
            Boolean skywalkingTraceIdEnabled = environment.getProperty(GatewayStrategyConstant.SPRING_APPLICATION_STRATEGY_GATEWAY_SKYWALKING_TRACEID_ENABLED, Boolean.class, Boolean.TRUE);
            if (skywalkingTraceIdEnabled) {
                return new SkyWalkingGatewayStrategyFilter();
            }

            return null;
        }
    }
}