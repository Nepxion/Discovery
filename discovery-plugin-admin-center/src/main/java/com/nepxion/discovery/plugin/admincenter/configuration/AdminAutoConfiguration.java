package com.nepxion.discovery.plugin.admincenter.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.discovery.plugin.admincenter.endpoint.AdminEndpoint;

@Configuration
// @ConditionalOnProperty(value = "spring.application.discovery.admin.enabled", matchIfMissing = true)
public class AdminAutoConfiguration {
    @ConditionalOnBean(ServiceRegistry.class)
    @ConditionalOnClass(Endpoint.class)
    protected static class AdminEndpointConfiguration {
        @Autowired(required = false)
        private Registration registration;

        @Bean
        public AdminEndpoint adminEndpoint(ServiceRegistry<?> serviceRegistry) {
            AdminEndpoint adminEndpoint = new AdminEndpoint(serviceRegistry);
            adminEndpoint.setRegistration(registration);

            return adminEndpoint;
        }
    }
}