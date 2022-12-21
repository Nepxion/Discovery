package com.nepxion.discovery.console.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.common.handler.DiscoveryResponseErrorHandler;
import com.nepxion.discovery.console.constant.ConsoleConstant;
import com.nepxion.discovery.console.endpoint.AuthenticationEndpoint;
import com.nepxion.discovery.console.endpoint.BlacklistEndpoint;
import com.nepxion.discovery.console.endpoint.ConfigEndpoint;
import com.nepxion.discovery.console.endpoint.FailoverEndpoint;
import com.nepxion.discovery.console.endpoint.InspectorEndpoint;
import com.nepxion.discovery.console.endpoint.RouteEndpoint;
import com.nepxion.discovery.console.endpoint.SentinelEndpoint;
import com.nepxion.discovery.console.endpoint.ServiceEndpoint;
import com.nepxion.discovery.console.endpoint.StrategyEndpoint;
import com.nepxion.discovery.console.endpoint.VersionEndpoint;
import com.nepxion.discovery.console.resource.AuthenticationResource;
import com.nepxion.discovery.console.resource.AuthenticationResourceImpl;
import com.nepxion.discovery.console.resource.BlacklistResource;
import com.nepxion.discovery.console.resource.BlacklistResourceImpl;
import com.nepxion.discovery.console.resource.ConfigResource;
import com.nepxion.discovery.console.resource.ConfigResourceImpl;
import com.nepxion.discovery.console.resource.FailoverResource;
import com.nepxion.discovery.console.resource.FailoverResourceImpl;
import com.nepxion.discovery.console.resource.InspectorResource;
import com.nepxion.discovery.console.resource.InspectorResourceImpl;
import com.nepxion.discovery.console.resource.RouteResource;
import com.nepxion.discovery.console.resource.RouteResourceImpl;
import com.nepxion.discovery.console.resource.SentinelResource;
import com.nepxion.discovery.console.resource.SentinelResourceImpl;
import com.nepxion.discovery.console.resource.ServiceResource;
import com.nepxion.discovery.console.resource.ServiceResourceImpl;
import com.nepxion.discovery.console.resource.StrategyResource;
import com.nepxion.discovery.console.resource.StrategyResourceImpl;
import com.nepxion.discovery.console.resource.VersionResource;
import com.nepxion.discovery.console.resource.VersionResourceImpl;

@Configuration
@Import({ SwaggerConfiguration.class, CorsRegistryConfiguration.class })
public class ConsoleAutoConfiguration {
    @ConditionalOnProperty(value = ConsoleConstant.SPRING_APPLICATION_CONSOLE_AUTHENTICATION_ENDPOINT_ENABLED, matchIfMissing = true)
    protected static class AuthenticationEndpointConfiguration {
        @Bean
        @ConditionalOnMissingBean
        public AuthenticationResource authenticationResource() {
            return new AuthenticationResourceImpl();
        }

        @Bean
        public AuthenticationEndpoint authenticationEndpoint() {
            return new AuthenticationEndpoint();
        }
    }

    @ConditionalOnProperty(value = ConsoleConstant.SPRING_APPLICATION_CONSOLE_SERVICE_ENDPOINT_ENABLED, matchIfMissing = true)
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

    @ConditionalOnProperty(value = ConsoleConstant.SPRING_APPLICATION_CONSOLE_CONFIG_ENDPOINT_ENABLED, matchIfMissing = true)
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

    @ConditionalOnProperty(value = ConsoleConstant.SPRING_APPLICATION_CONSOLE_VERSION_ENDPOINT_ENABLED, matchIfMissing = true)
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

    @ConditionalOnProperty(value = ConsoleConstant.SPRING_APPLICATION_CONSOLE_SENTINEL_ENDPOINT_ENABLED, matchIfMissing = true)
    protected static class SentinelEndpointConfiguration {
        @Bean
        public SentinelResource sentinelResource() {
            return new SentinelResourceImpl();
        }

        @Bean
        public SentinelEndpoint sentinelEndpoint() {
            return new SentinelEndpoint();
        }
    }

    @ConditionalOnProperty(value = ConsoleConstant.SPRING_APPLICATION_CONSOLE_ROUTE_ENDPOINT_ENABLED, matchIfMissing = true)
    protected static class RouteEndpointConfiguration {
        @Bean
        public RouteResource routeResource() {
            return new RouteResourceImpl();
        }

        @Bean
        public RouteEndpoint routeEndpoint() {
            return new RouteEndpoint();
        }
    }

    @ConditionalOnProperty(value = ConsoleConstant.SPRING_APPLICATION_CONSOLE_STRATEGY_ENDPOINT_ENABLED, matchIfMissing = true)
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

    @ConditionalOnProperty(value = ConsoleConstant.SPRING_APPLICATION_CONSOLE_FAILOVER_ENDPOINT_ENABLED, matchIfMissing = true)
    protected static class FailoverEndpointConfiguration {
        @Bean
        public FailoverResource failoverResource() {
            return new FailoverResourceImpl();
        }

        @Bean
        public FailoverEndpoint failoverEndpoint() {
            return new FailoverEndpoint();
        }
    }

    @ConditionalOnProperty(value = ConsoleConstant.SPRING_APPLICATION_CONSOLE_BLACKLIST_ENDPOINT_ENABLED, matchIfMissing = true)
    protected static class BlacklistEndpointConfiguration {
        @Bean
        public BlacklistResource blacklistResource() {
            return new BlacklistResourceImpl();
        }

        @Bean
        public BlacklistEndpoint blacklistEndpoint() {
            return new BlacklistEndpoint();
        }
    }

    @ConditionalOnProperty(value = ConsoleConstant.SPRING_APPLICATION_CONSOLE_INSPECTOR_ENDPOINT_ENABLED, matchIfMissing = true)
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

    protected static class RestTemplateEndpointConfiguration {
        @Bean
        public RestTemplate consoleRestTemplate() {
            RestTemplate consoleRestTemplate = new RestTemplate();
            consoleRestTemplate.setErrorHandler(new DiscoveryResponseErrorHandler());

            return consoleRestTemplate;
        }

        @Bean
        @LoadBalanced
        public RestTemplate loadBalancedRestTemplate() {
            return new RestTemplate();
        }
    }
}