package com.nepxion.discovery.common.consul.configuration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.ecwid.consul.v1.ConsulClient;
import com.nepxion.discovery.common.consul.constant.ConsulConstant;
import com.nepxion.discovery.common.consul.operation.ConsulOperation;

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
            throw new IllegalArgumentException(ConsulConstant.CONSUL_HOST + " can't be null or empty");
        }

        int consulPort = environment.getProperty(ConsulConstant.CONSUL_PORT, int.class);

        ConsulClient consulClient = new ConsulClient(consulHost, consulPort);

        return consulClient;
    }
}