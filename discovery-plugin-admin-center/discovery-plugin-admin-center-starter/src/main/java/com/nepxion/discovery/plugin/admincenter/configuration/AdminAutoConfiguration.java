package com.nepxion.discovery.plugin.admincenter.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Ning Zhang
 * @version 1.0
 */

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.nepxion.discovery.plugin.admincenter.constant.AdminConstant;
import com.nepxion.discovery.plugin.admincenter.endpoint.ConfigEndpoint;
import com.nepxion.discovery.plugin.admincenter.endpoint.GatewayStrategyRouteEndpoint;
import com.nepxion.discovery.plugin.admincenter.endpoint.GitEndpoint;
import com.nepxion.discovery.plugin.admincenter.endpoint.InspectorEndpoint;
import com.nepxion.discovery.plugin.admincenter.endpoint.RouterEndpoint;
import com.nepxion.discovery.plugin.admincenter.endpoint.SentinelCoreEndpoint;
import com.nepxion.discovery.plugin.admincenter.endpoint.SentinelParamEndpoint;
import com.nepxion.discovery.plugin.admincenter.endpoint.ServiceEndpoint;
import com.nepxion.discovery.plugin.admincenter.endpoint.StrategyEndpoint;
import com.nepxion.discovery.plugin.admincenter.endpoint.VersionEndpoint;
import com.nepxion.discovery.plugin.admincenter.endpoint.ZuulStrategyRouteEndpoint;
import com.nepxion.discovery.plugin.admincenter.resource.ConfigResource;
import com.nepxion.discovery.plugin.admincenter.resource.ConfigResourceImpl;
import com.nepxion.discovery.plugin.admincenter.resource.GatewayStrategyRouteResource;
import com.nepxion.discovery.plugin.admincenter.resource.GatewayStrategyRouteResourceImpl;
import com.nepxion.discovery.plugin.admincenter.resource.GitResource;
import com.nepxion.discovery.plugin.admincenter.resource.GitResourceImpl;
import com.nepxion.discovery.plugin.admincenter.resource.InspectorResource;
import com.nepxion.discovery.plugin.admincenter.resource.InspectorResourceImpl;
import com.nepxion.discovery.plugin.admincenter.resource.RouterResource;
import com.nepxion.discovery.plugin.admincenter.resource.RouterResourceImpl;
import com.nepxion.discovery.plugin.admincenter.resource.SentinelCoreResource;
import com.nepxion.discovery.plugin.admincenter.resource.SentinelCoreResourceImpl;
import com.nepxion.discovery.plugin.admincenter.resource.SentinelParamResource;
import com.nepxion.discovery.plugin.admincenter.resource.SentinelParamResourceImpl;
import com.nepxion.discovery.plugin.admincenter.resource.ServiceResource;
import com.nepxion.discovery.plugin.admincenter.resource.ServiceResourceImpl;
import com.nepxion.discovery.plugin.admincenter.resource.StrategyResource;
import com.nepxion.discovery.plugin.admincenter.resource.StrategyResourceImpl;
import com.nepxion.discovery.plugin.admincenter.resource.VersionResource;
import com.nepxion.discovery.plugin.admincenter.resource.VersionResourceImpl;
import com.nepxion.discovery.plugin.admincenter.resource.ZuulStrategyRouteResource;
import com.nepxion.discovery.plugin.admincenter.resource.ZuulStrategyRouteResourceImpl;
import com.nepxion.discovery.plugin.framework.generator.GitGenerator;
import com.nepxion.discovery.plugin.strategy.gateway.route.GatewayStrategyRoute;
import com.nepxion.discovery.plugin.strategy.wrapper.StrategyWrapper;
import com.nepxion.discovery.plugin.strategy.zuul.route.ZuulStrategyRoute;

@Configuration
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
public class AdminAutoConfiguration {
    @ConditionalOnProperty(value = AdminConstant.SPRING_APPLICATION_ADMIN_SERVICE_ENDPOINT_ENABLED, matchIfMissing = true)
    protected static class ServiceEndpointConfiguration {
        @Bean
        public ServiceResource serviceResource() {
            return new ServiceResourceImpl();
        }

        @Bean
        public ServiceEndpoint serviceEndpoint() {
            return new ServiceEndpoint();
        }
    }

    @ConditionalOnProperty(value = AdminConstant.SPRING_APPLICATION_ADMIN_CONFIG_ENDPOINT_ENABLED, matchIfMissing = true)
    protected static class ConfigEndpointConfiguration {
        @Bean
        public ConfigResource configResource() {
            return new ConfigResourceImpl();
        }

        @Bean
        public ConfigEndpoint configEndpoint() {
            return new ConfigEndpoint();
        }
    }

    @ConditionalOnProperty(value = AdminConstant.SPRING_APPLICATION_ADMIN_VERSION_ENDPOINT_ENABLED, matchIfMissing = true)
    protected static class VersionEndpointConfiguration {
        @Bean
        public VersionResource versionResource() {
            return new VersionResourceImpl();
        }

        @Bean
        public VersionEndpoint versionEndpoint() {
            return new VersionEndpoint();
        }
    }

    @ConditionalOnProperty(value = AdminConstant.SPRING_APPLICATION_ADMIN_INSPECTOR_ENDPOINT_ENABLED, matchIfMissing = true)
    protected static class InspectorEndpointConfiguration {
        @Bean
        public InspectorResource inspectorResource() {
            return new InspectorResourceImpl();
        }

        @Bean
        public InspectorEndpoint inspectorEndpoint() {
            return new InspectorEndpoint();
        }
    }

    @ConditionalOnProperty(value = AdminConstant.SPRING_APPLICATION_ADMIN_ROUTER_ENDPOINT_ENABLED, matchIfMissing = true)
    protected static class RouterEndpointConfiguration {
        @Bean
        public RouterResource routerResource() {
            return new RouterResourceImpl();
        }

        @Bean
        public RouterEndpoint routerEndpoint() {
            return new RouterEndpoint();
        }
    }

    @ConditionalOnClass(StrategyWrapper.class)
    @ConditionalOnProperty(value = AdminConstant.SPRING_APPLICATION_ADMIN_STRATEGY_ENDPOINT_ENABLED, matchIfMissing = true)
    protected static class StrategyEndpointConfiguration {
        @Bean
        public StrategyResource strategyResource() {
            return new StrategyResourceImpl();
        }

        @Bean
        public StrategyEndpoint strategyEndpoint() {
            return new StrategyEndpoint();
        }
    }

    @ConditionalOnClass(FlowRule.class)
    @ConditionalOnProperty(value = AdminConstant.SPRING_APPLICATION_ADMIN_SENTINEL_ENDPOINT_ENABLED, matchIfMissing = true)
    protected static class SentinelCoreEndpointConfiguration {
        @Bean
        public SentinelCoreResource sentinelCoreResource() {
            return new SentinelCoreResourceImpl();
        }

        @Bean
        public SentinelCoreEndpoint sentinelCoreEndpoint() {
            return new SentinelCoreEndpoint();
        }
    }

    @ConditionalOnClass(ParamFlowRule.class)
    @ConditionalOnProperty(value = AdminConstant.SPRING_APPLICATION_ADMIN_SENTINEL_ENDPOINT_ENABLED, matchIfMissing = true)
    protected static class SentinelParamEndpointConfiguration {
        @Bean
        public SentinelParamResource sentinelParamResource() {
            return new SentinelParamResourceImpl();
        }

        @Bean
        public SentinelParamEndpoint sentinelParamEndpoint() {
            return new SentinelParamEndpoint();
        }
    }

    @ConditionalOnBean(GitGenerator.class)
    @ConditionalOnProperty(value = AdminConstant.SPRING_APPLICATION_ADMIN_GIT_ENDPOINT_ENABLED, matchIfMissing = true)
    protected static class GitEndpointConfiguration {
        @Bean
        public GitResource gitResource() {
            return new GitResourceImpl();
        }

        @Bean
        public GitEndpoint gitEndpoint() {
            return new GitEndpoint();
        }
    }

    @ConditionalOnBean(GatewayStrategyRoute.class)
    @ConditionalOnProperty(value = AdminConstant.SPRING_APPLICATION_ADMIN_GATEWAY_ENDPOINT_ENABLED, matchIfMissing = true)
    protected static class GatewayStrategyRouteEndpointConfiguration {
        @Bean
        @ConditionalOnProperty(value = "spring.cloud.gateway.discovery.locator.enabled", havingValue = "false", matchIfMissing = true)
        public GatewayStrategyRouteResource gatewayStrategyRouteResource() {
            return new GatewayStrategyRouteResourceImpl();
        }

        @Bean
        @ConditionalOnProperty(value = "spring.cloud.gateway.discovery.locator.enabled", havingValue = "false", matchIfMissing = true)
        public GatewayStrategyRouteEndpoint gatewayStrategyRouteEndpoint() {
            return new GatewayStrategyRouteEndpoint();
        }
    }

    @ConditionalOnBean(ZuulStrategyRoute.class)
    @ConditionalOnProperty(value = AdminConstant.SPRING_APPLICATION_ADMIN_ZUUL_ENDPOINT_ENABLED, matchIfMissing = true)
    protected static class ZuulStrategyRouteEndpointConfiguration {
        @Bean
        public ZuulStrategyRouteResource zuulStrategyRouteResource() {
            return new ZuulStrategyRouteResourceImpl();
        }

        @Bean
        public ZuulStrategyRouteEndpoint zuulStrategyRouteEndpoint() {
            return new ZuulStrategyRouteEndpoint();
        }
    }

    @ConditionalOnClass(WebMvcConfigurer.class)
    protected static class WebMvcActivationConfiguration {
        @Bean
        @ConditionalOnProperty(value = "cors.registry.enabled", matchIfMissing = false)
        public CorsRegistryConfiguration corsRegistryConfiguration() {
            return new CorsRegistryConfiguration();
        }
    }
}