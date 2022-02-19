package com.nepxion.discovery.plugin.strategy.zuul.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Ning Zhang
 * @version 1.0
 */

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.discovery.common.apollo.proccessor.ApolloProcessor;
import com.nepxion.discovery.common.consul.proccessor.ConsulProcessor;
import com.nepxion.discovery.common.etcd.proccessor.EtcdProcessor;
import com.nepxion.discovery.common.nacos.proccessor.NacosProcessor;
import com.nepxion.discovery.common.redis.proccessor.RedisProcessor;
import com.nepxion.discovery.common.zookeeper.proccessor.ZookeeperProcessor;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.zuul.constant.ZuulStrategyConstant;
import com.nepxion.discovery.plugin.strategy.zuul.context.ZuulStrategyContextListener;
import com.nepxion.discovery.plugin.strategy.zuul.filter.DefaultZuulStrategyClearFilter;
import com.nepxion.discovery.plugin.strategy.zuul.filter.DefaultZuulStrategyRouteFilter;
import com.nepxion.discovery.plugin.strategy.zuul.filter.ZuulStrategyClearFilter;
import com.nepxion.discovery.plugin.strategy.zuul.filter.ZuulStrategyRouteFilter;
import com.nepxion.discovery.plugin.strategy.zuul.monitor.DefaultZuulStrategyMonitor;
import com.nepxion.discovery.plugin.strategy.zuul.monitor.ZuulStrategyMonitor;
import com.nepxion.discovery.plugin.strategy.zuul.processor.ZuulStrategyRouteApolloProcessor;
import com.nepxion.discovery.plugin.strategy.zuul.processor.ZuulStrategyRouteConsulProcessor;
import com.nepxion.discovery.plugin.strategy.zuul.processor.ZuulStrategyRouteEtcdProcessor;
import com.nepxion.discovery.plugin.strategy.zuul.processor.ZuulStrategyRouteNacosProcessor;
import com.nepxion.discovery.plugin.strategy.zuul.processor.ZuulStrategyRouteRedisProcessor;
import com.nepxion.discovery.plugin.strategy.zuul.processor.ZuulStrategyRouteZookeeperProcessor;
import com.nepxion.discovery.plugin.strategy.zuul.route.DefaultZuulStrategyRoute;
import com.nepxion.discovery.plugin.strategy.zuul.route.ZuulStrategyRoute;
import com.nepxion.discovery.plugin.strategy.zuul.wrapper.DefaultZuulStrategyCallableWrapper;
import com.nepxion.discovery.plugin.strategy.zuul.wrapper.ZuulStrategyCallableWrapper;

@Configuration
@AutoConfigureBefore(RibbonClientConfiguration.class)
public class ZuulStrategyAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ZuulStrategyRouteFilter zuulStrategyRouteFilter() {
        return new DefaultZuulStrategyRouteFilter();
    }

    // 只用于监控模块的调用链
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_MONITOR_ENABLED, matchIfMissing = false)
    public ZuulStrategyClearFilter zuulStrategyClearFilter() {
        return new DefaultZuulStrategyClearFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_MONITOR_ENABLED, matchIfMissing = false)
    public ZuulStrategyMonitor zuulStrategyMonitor() {
        return new DefaultZuulStrategyMonitor();
    }

    @Bean
    @ConditionalOnMissingBean
    public ZuulStrategyRoute zuulStrategyRoute(ServerProperties serverProperties, ZuulProperties zuulProperties) {
        return new DefaultZuulStrategyRoute(serverProperties.getServlet().getContextPath(), zuulProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_HYSTRIX_THREADLOCAL_SUPPORTED, matchIfMissing = false)
    public ZuulStrategyCallableWrapper zuulStrategyCallableWrapper() {
        return new DefaultZuulStrategyCallableWrapper();
    }

    @Bean
    public ZuulStrategyContextListener zuulStrategyContextListener() {
        return new ZuulStrategyContextListener();
    }

    @ConditionalOnClass(NacosProcessor.class)
    @ConditionalOnProperty(value = ZuulStrategyConstant.SPRING_APPLICATION_STRATEGY_ZUUL_DYNAMIC_ROUTE_ENABLED, matchIfMissing = false)
    protected static class ZuulRouteNacosConfiguration {
        @Bean
        public NacosProcessor zuulStrategyRouteNacosProcessor() {
            return new ZuulStrategyRouteNacosProcessor();
        }
    }

    @ConditionalOnClass(ApolloProcessor.class)
    @ConditionalOnProperty(value = ZuulStrategyConstant.SPRING_APPLICATION_STRATEGY_ZUUL_DYNAMIC_ROUTE_ENABLED, matchIfMissing = false)
    protected static class ZuulRouteApolloConfiguration {
        @Bean
        public ApolloProcessor zuulStrategyRouteApolloProcessor() {
            return new ZuulStrategyRouteApolloProcessor();
        }
    }

    @ConditionalOnClass(RedisProcessor.class)
    @ConditionalOnProperty(value = ZuulStrategyConstant.SPRING_APPLICATION_STRATEGY_ZUUL_DYNAMIC_ROUTE_ENABLED, matchIfMissing = false)
    protected static class ZuulRouteRedisConfiguration {
        @Bean
        public RedisProcessor zuulStrategyRouteRedisProcessor() {
            return new ZuulStrategyRouteRedisProcessor();
        }
    }

    @ConditionalOnClass(ZookeeperProcessor.class)
    @ConditionalOnProperty(value = ZuulStrategyConstant.SPRING_APPLICATION_STRATEGY_ZUUL_DYNAMIC_ROUTE_ENABLED, matchIfMissing = false)
    protected static class ZuulRouteZookeeperConfiguration {
        @Bean
        public ZookeeperProcessor zuulStrategyRouteZookeeperProcessor() {
            return new ZuulStrategyRouteZookeeperProcessor();
        }
    }

    @ConditionalOnClass(ConsulProcessor.class)
    @ConditionalOnProperty(value = ZuulStrategyConstant.SPRING_APPLICATION_STRATEGY_ZUUL_DYNAMIC_ROUTE_ENABLED, matchIfMissing = false)
    protected static class ZuulRouteConsulConfiguration {
        @Bean
        public ConsulProcessor zuulStrategyRouteConsulProcessor() {
            return new ZuulStrategyRouteConsulProcessor();
        }
    }

    @ConditionalOnClass(EtcdProcessor.class)
    @ConditionalOnProperty(value = ZuulStrategyConstant.SPRING_APPLICATION_STRATEGY_ZUUL_DYNAMIC_ROUTE_ENABLED, matchIfMissing = false)
    protected static class ZuulRouteEtcdConfiguration {
        @Bean
        public EtcdProcessor zuulStrategyRouteEtcdProcessor() {
            return new ZuulStrategyRouteEtcdProcessor();
        }
    }
}