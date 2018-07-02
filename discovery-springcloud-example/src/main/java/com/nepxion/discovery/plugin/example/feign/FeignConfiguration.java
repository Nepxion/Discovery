package com.nepxion.discovery.plugin.example.feign;

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

import com.nepxion.discovery.plugin.framework.context.PluginFeignBeanFactoryPostProcessor;

@Configuration
public class FeignConfiguration {
    @Bean
    public PluginFeignBeanFactoryPostProcessor pluginFeignBeanFactoryPostProcessor() {
        return new PluginFeignBeanFactoryPostProcessor();
    }
}
