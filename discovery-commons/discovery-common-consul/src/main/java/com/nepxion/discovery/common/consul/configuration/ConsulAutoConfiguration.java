package com.nepxion.discovery.common.consul.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Congwei Xu
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.ecwid.consul.v1.ConsulClient;
import com.nepxion.discovery.common.consul.constant.ConsulConstant;
import com.nepxion.discovery.common.consul.operation.ConsulOperation;
import com.nepxion.discovery.common.exception.DiscoveryException;

@Configuration
public class ConsulAutoConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public ConsulOperation consulOperation() {
        return new ConsulOperation();
    }

    @Bean
    @ConditionalOnMissingBean
    public ConsulClient consulClient() {
        String consulHost = environment.getProperty(ConsulConstant.CONSUL_HOST);
        if (StringUtils.isBlank(consulHost)) {
            throw new DiscoveryException(ConsulConstant.CONSUL_HOST + " can't be null or empty");
        }

        int consulPort = environment.getProperty(ConsulConstant.CONSUL_PORT, Integer.class);

        return new ConsulClient(consulHost, consulPort);
    }
}