package com.nepxion.discovery.plugin.strategy.aop;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.web.client.RestTemplate;

public class RestTemplateStrategyBeanPostProcessor implements BeanPostProcessor {
    @Autowired
    protected RestTemplateStrategyInterceptor restTemplateStrategyInterceptor;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof RestTemplate) {
            RestTemplate restTemplate = (RestTemplate) bean;
            restTemplate.getInterceptors().add(restTemplateStrategyInterceptor);
        }

        return bean;
    }
}