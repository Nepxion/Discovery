package com.nepxion.discovery.plugin.configcenter.redis.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.nepxion.discovery.common.redis.constant.RedisConstant;
import com.nepxion.discovery.plugin.configcenter.redis.adapter.RedisConfigAdapter;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;

@Configuration
public class RedisConfigAutoConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(RedisConfigAutoConfiguration.class);

    static {
        System.out.println("");
        System.out.println("╔═══╗    ╔╗");
        System.out.println("║╔═╗║    ║║");
        System.out.println("║╚═╝╠══╦═╝╠╦══╗");
        System.out.println("║╔╗╔╣║═╣╔╗╠╣══╣");
        System.out.println("║║║╚╣║═╣╚╝║╠══║");
        System.out.println("╚╝╚═╩══╩══╩╩══╝");
        System.out.println(RedisConstant.TYPE + " Config");
        System.out.println("");
    }

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    protected PluginContextAware pluginContextAware;

    @Autowired
    private PluginAdapter pluginAdapter;

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(MessageListenerAdapter partialMessageListenerAdapter, MessageListenerAdapter globalMessageListenerAdapter) {
        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();

        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        redisMessageListenerContainer.addMessageListener(partialMessageListenerAdapter, new PatternTopic(group + "-" + serviceId));
        redisMessageListenerContainer.addMessageListener(globalMessageListenerAdapter, new PatternTopic(group + "-" + group));

        return redisMessageListenerContainer;
    }

    @Bean
    public MessageListenerAdapter partialMessageListenerAdapter(RedisConfigAdapter configAdapter) {
        String groupKey = pluginContextAware.getGroupKey();
        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();

        LOG.info("Subscribe {} config from {} server, {}={}, serviceId={}", configAdapter.getConfigScope(false), configAdapter.getConfigType(), groupKey, group, serviceId);

        return new MessageListenerAdapter(configAdapter, "subscribePartialConfig");
    }

    @Bean
    public MessageListenerAdapter globalMessageListenerAdapter(RedisConfigAdapter configAdapter) {
        String groupKey = pluginContextAware.getGroupKey();
        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();

        LOG.info("Subscribe {} config from {} server, {}={}, serviceId={}", configAdapter.getConfigScope(true), configAdapter.getConfigType(), groupKey, group, serviceId);

        return new MessageListenerAdapter(configAdapter, "subscribeGlobalConfig");
    }

    @Bean
    public RedisConfigAdapter configAdapter() {
        return new RedisConfigAdapter();
    }
}