package com.nepxion.discovery.plugin.configcenter.extension.redis.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.nepxion.discovery.plugin.configcenter.ConfigAdapter;
import com.nepxion.discovery.plugin.configcenter.extension.redis.adapter.RedisConfigAdapter;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;

@Configuration
public class RedisConfigAutoConfiguration {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private PluginAdapter pluginAdapter;

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(MessageListenerAdapter messageListenerAdapter) {
        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();

        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter, new PatternTopic(group + "-" + serviceId));

        return redisMessageListenerContainer;
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter(ConfigAdapter configAdapter) {
        return new MessageListenerAdapter(configAdapter, "subscribeConfig");
    }

    @Bean
    public ConfigAdapter configAdapter() {
        return new RedisConfigAdapter();
    }
}