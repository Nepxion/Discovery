package com.nepxion.discovery.common.zookeeper.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author rotten
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.zookeeper.constant.ZookeeperConstant;
import com.nepxion.discovery.common.zookeeper.operation.ZookeeperOperation;

@Configuration
public class ZookeeperAutoConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public ZookeeperOperation zookeeperOperation() {
        return new ZookeeperOperation();
    }

    @Bean
    @ConditionalOnMissingBean
    public CuratorFramework curatorFramework() {
        String connectString = environment.getProperty(ZookeeperConstant.ZOOKEEPER_CONNECT_STRING);
        if (StringUtils.isEmpty(connectString)) {
            throw new DiscoveryException(ZookeeperConstant.ZOOKEEPER_CONNECT_STRING + " can't be null or empty");
        }

        int retryCount = environment.getProperty(ZookeeperConstant.ZOOKEEPER_RETRY_COUNT, Integer.class, ZookeeperConstant.ZOOKEEPER_DEFAULT_RETRY_COUNT_VALUE);
        int sleepTime = environment.getProperty(ZookeeperConstant.ZOOKEEPER_SLEEP_TIME, Integer.class, ZookeeperConstant.ZOOKEEPER_DEFAULT_SLEEP_TIME_VALUE);

        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().connectString(connectString).retryPolicy(new ExponentialBackoffRetry(sleepTime, retryCount)).build();
        curatorFramework.start();

        return curatorFramework;
    }
}