package com.nepxion.discovery.plugin.admincenter.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.nepxion.discovery.plugin.admincenter.endpoint.ConfigEndpoint;
import com.nepxion.discovery.plugin.admincenter.endpoint.RouterEndpoint;
import com.nepxion.discovery.plugin.admincenter.endpoint.SentinelCoreEndpoint;
import com.nepxion.discovery.plugin.admincenter.endpoint.SentinelParamEndpoint;
import com.nepxion.discovery.plugin.admincenter.endpoint.VersionEndpoint;

@Configuration
@Import({ SwaggerConfiguration.class, CorsRegistryConfiguration.class })
public class AdminAutoConfiguration {
    @ConditionalOnClass(Endpoint.class)
    protected static class AdminEndpointConfiguration {
        @Bean
        public ConfigEndpoint configEndpoint() {
            return new ConfigEndpoint();
        }

        @Bean
        public VersionEndpoint versionEndpoint() {
            return new VersionEndpoint();
        }

        @Bean
        public RouterEndpoint routerEndpoint() {
            return new RouterEndpoint();
        }

        @Bean
        public RestTemplate routerRestTemplate() {
            return new RestTemplate();
        }
    }

    @ConditionalOnClass({ Endpoint.class, FlowRule.class })
    protected static class SentinelCoreEndpointConfiguration {
        @Bean
        public SentinelCoreEndpoint sentinelCoreEndpoint() {
            return new SentinelCoreEndpoint();
        }
    }

    @ConditionalOnClass({ Endpoint.class, ParamFlowRule.class })
    protected static class SentinelParamEndpointConfiguration {
        @Bean
        public SentinelParamEndpoint sentinelParamEndpoint() {
            return new SentinelParamEndpoint();
        }
    }
}