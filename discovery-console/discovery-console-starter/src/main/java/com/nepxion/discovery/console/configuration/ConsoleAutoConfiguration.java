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

import com.nepxion.discovery.console.authentication.AuthenticationResource;
import com.nepxion.discovery.console.authentication.AuthenticationResourceImpl;
import com.nepxion.discovery.console.endpoint.ConsoleEndpoint;

@Configuration
@Import({ SwaggerConfiguration.class, CorsRegistryConfiguration.class })
public class ConsoleAutoConfiguration {
    protected static class ConsoleEndpointConfiguration {
        @Bean
        public ConsoleEndpoint consoleEndpoint() {
            return new ConsoleEndpoint();
        }

        @Bean
        @ConditionalOnMissingBean
        public AuthenticationResource authenticationResource() {
            return new AuthenticationResourceImpl();
        }
    }
}