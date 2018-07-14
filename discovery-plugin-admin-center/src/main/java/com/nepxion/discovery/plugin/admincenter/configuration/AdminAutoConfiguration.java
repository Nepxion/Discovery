package com.nepxion.discovery.plugin.admincenter.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.plugin.admincenter.endpoint.ConfigEndpoint;
import com.nepxion.discovery.plugin.admincenter.endpoint.RouterEndpoint;
import com.nepxion.discovery.plugin.admincenter.endpoint.VersionEndpoint;

@Configuration
@Import(SwaggerConfiguration.class)
public class AdminAutoConfiguration {
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
}