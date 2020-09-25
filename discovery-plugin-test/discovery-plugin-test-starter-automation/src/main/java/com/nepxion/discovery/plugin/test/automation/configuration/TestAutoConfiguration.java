package com.nepxion.discovery.plugin.test.automation.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.test.automation.aop.TestAutoScanProxy;
import com.nepxion.discovery.plugin.test.automation.aop.TestInterceptor;
import com.nepxion.discovery.plugin.test.automation.constant.TestConstant;
import com.nepxion.discovery.plugin.test.automation.operation.TestOperation;

@Configuration
public class TestAutoConfiguration {
    @Autowired
    private ConfigurableEnvironment environment;

    @Bean
    public TestAutoScanProxy testAutoScanProxy() {
        String scanPackages = environment.getProperty(TestConstant.SPRING_APPLICATION_TEST_SCAN_PACKAGES);
        if (StringUtils.isEmpty(scanPackages)) {
            throw new DiscoveryException(TestConstant.SPRING_APPLICATION_TEST_SCAN_PACKAGES + "'s value can't be empty");
        }

        return new TestAutoScanProxy(scanPackages);
    }

    @Bean
    public TestInterceptor testInterceptor() {
        String scanPackages = environment.getProperty(TestConstant.SPRING_APPLICATION_TEST_SCAN_PACKAGES);
        if (StringUtils.isEmpty(scanPackages)) {
            throw new DiscoveryException(TestConstant.SPRING_APPLICATION_TEST_SCAN_PACKAGES + "'s value can't be empty");
        }

        return new TestInterceptor();
    }

    @Bean
    public TestOperation testOperation() {
        return new TestOperation();
    }
}