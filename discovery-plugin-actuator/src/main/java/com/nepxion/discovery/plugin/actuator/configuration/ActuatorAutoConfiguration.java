package com.nepxion.discovery.plugin.actuator.configuration;

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

import com.nepxion.discovery.plugin.actuator.endpoint.ActuatorEndpoint;

@Configuration
//@ConditionalOnProperty(value = "com.bkjk.platform.restclient.enabled", matchIfMissing = true)
public class ActuatorAutoConfiguration {
//
//    @ConditionalOnBean(ServiceRegistry.class)
//    @ConditionalOnClass(Endpoint.class)
//    protected static class EurekaMgmtEndpointConfiguration {
//        @Autowired(required = false)
//        private Registration registration;
//
//        @Bean
//        public EurekaMgmtEndpoint serviceDiscoveryMgmtEndpoint(ServiceRegistry serviceRegistry) {
//            EurekaMgmtEndpoint endpoint = new EurekaMgmtEndpoint(serviceRegistry);
//            endpoint.setRegistration(registration);
//            return endpoint;
//        }
//    }
    
    @Bean
    public ActuatorEndpoint actuatorEndpoint() {
        return new ActuatorEndpoint();
    }
}