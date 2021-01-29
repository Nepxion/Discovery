package com.nepxion.discovery.common.etcd.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Congwei Xu
 * @version 1.0
 */

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.ClientBuilder;

import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.nepxion.discovery.common.etcd.constant.EtcdConstant;
import com.nepxion.discovery.common.etcd.operation.EtcdOperation;

@Configuration
public class EtcdAutoConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public EtcdOperation etcdOperation() {
        return new EtcdOperation();
    }

    @Bean
    @ConditionalOnMissingBean
    public Client client() {
        String serverAddr = environment.getProperty(EtcdConstant.ETCD_SERVER_ADDR);
        if (StringUtils.isBlank(serverAddr)) {
            throw new IllegalArgumentException(EtcdConstant.ETCD_SERVER_ADDR + " can't be null or empty");
        }

        ClientBuilder clientBuilder = Client.builder().endpoints(serverAddr);

        String username = environment.getProperty(EtcdConstant.ETCD_USERNAME);
        if (StringUtils.isNotBlank(username)) {
            clientBuilder.user(ByteSequence.from(username, StandardCharsets.UTF_8));
        }

        String password = environment.getProperty(EtcdConstant.ETCD_PASSWORD);
        if (StringUtils.isNotBlank(password)) {
            clientBuilder.password(ByteSequence.from(password, StandardCharsets.UTF_8));
        }
        clientBuilder.loadBalancerPolicy(EtcdConstant.ETCD_CLIENT_ROUND_ROBIN);

        return clientBuilder.build();
    }
}