package com.nepxion.discovery.plugin.example.service.feign;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {
    // 该Issue只在Eureka下才会出现
    @ConditionalOnClass(name = "org.springframework.cloud.netflix.eureka.serviceregistry.EurekaServiceRegistry")
    protected static class FeignBeanFactoryPostProcessorConfiguration {
        @Bean
        public BeanFactoryPostProcessor feignBeanFactoryPostProcessor() {
            return new FeignBeanFactoryPostProcessor();
        }
    }

    // 参考：https://github.com/spring-cloud/spring-cloud-netflix/issues/1952, https://github.com/spring-cloud/spring-cloud-netflix/issues/1064
    protected static class FeignBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            BeanDefinition definition = beanFactory.getBeanDefinition("feignContext");
            definition.setDependsOn("eurekaServiceRegistry", "inetUtils");
        }
    }
}