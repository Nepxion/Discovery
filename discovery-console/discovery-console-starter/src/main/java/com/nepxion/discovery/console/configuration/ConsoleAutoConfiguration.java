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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.common.handler.DiscoveryResponseErrorHandler;
import com.nepxion.discovery.console.endpoint.AuthenticationEndpoint;
import com.nepxion.discovery.console.endpoint.ConfigEndpoint;
import com.nepxion.discovery.console.endpoint.RouteEndpoint;
import com.nepxion.discovery.console.endpoint.SentinelEndpoint;
import com.nepxion.discovery.console.endpoint.ServiceEndpoint;
import com.nepxion.discovery.console.endpoint.StrategyEndpoint;
import com.nepxion.discovery.console.endpoint.VersionEndpoint;
import com.nepxion.discovery.console.resource.AuthenticationResource;
import com.nepxion.discovery.console.resource.AuthenticationResourceImpl;
import com.nepxion.discovery.console.resource.ConfigResource;
import com.nepxion.discovery.console.resource.ConfigResourceImpl;
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
    protected static class ConsoleEndpointConfiguration {
        @Bean
        @ConditionalOnMissingBean
        public AuthenticationResource authenticationResource() {
            return new AuthenticationResourceImpl();
        }

        @Bean
        public AuthenticationEndpoint authenticationEndpoint() {
            return new AuthenticationEndpoint();
        }

        @Bean
        public ServiceResource serviceResource() {
            return new ServiceResourceImpl();
        }

        @Bean
        public ServiceEndpoint serviceEndpoint() {
            return new ServiceEndpoint();
        }

        @Bean
        public ConfigResource configResource() {
            return new ConfigResourceImpl();
        }

        @Bean
        public ConfigEndpoint configEndpoint() {
            return new ConfigEndpoint();
        }

        @Bean
        public VersionResource versionResource() {
            return new VersionResourceImpl();
        }

        @Bean
        public VersionEndpoint versionEndpoint() {
            return new VersionEndpoint();
        }

        @Bean
        public SentinelResource sentinelResource() {
            return new SentinelResourceImpl();
        }

        @Bean
        public SentinelEndpoint sentinelEndpoint() {
            return new SentinelEndpoint();
        }

        @Bean
        public RouteResource routeResource() {
            return new RouteResourceImpl();
        }

        @Bean
        public RouteEndpoint routeEndpoint() {
            return new RouteEndpoint();
        }

        @Bean
        public StrategyResource strategyResource() {
            return new StrategyResourceImpl();
        }

        @Bean
        public StrategyEndpoint strategyEndpoint() {
            return new StrategyEndpoint();
        }

        @Bean
        public RestTemplate consoleRestTemplate() {
            RestTemplate consoleRestTemplate = new RestTemplate();
            consoleRestTemplate.setErrorHandler(new DiscoveryResponseErrorHandler());

            return consoleRestTemplate;
        }
    }
}