package com.nepxion.discovery.plugin.strategy.service.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.plugin.strategy.service.filter.RequestBodyFilter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.discovery.plugin.strategy.service.context.ServiceStrategyContextHolder;

@Configuration
@AutoConfigureBefore(RibbonClientConfiguration.class)
public class ServiceStrategyContextAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ServiceStrategyContextHolder serviceStrategyContextHolder() {
        return new ServiceStrategyContextHolder();
    }

    @Bean
    public FilterRegistrationBean requestBodyFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new RequestBodyFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName(RequestBodyFilter.FILTER_NAME);
        registrationBean.setOrder(1);
        return registrationBean;
    }
}