package com.nepxion.discovery.plugin.test.automation.aop;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.plugin.test.automation.annotation.DTest;
import com.nepxion.discovery.plugin.test.automation.annotation.DTestConfig;
import com.nepxion.discovery.plugin.test.automation.constant.TestConstant;
import com.nepxion.discovery.plugin.test.automation.operation.TestOperation;
import com.nepxion.matrix.proxy.aop.AbstractInterceptor;

public class TestInterceptor extends AbstractInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(TestInterceptor.class);

    @Autowired
    private TestOperation testOperation;

    @Value("${" + TestConstant.SPRING_APPLICATION_TEST_CONFIG_OPERATION_AWAIT_TIME + ":3000}")
    private Integer configOperationAwaitTime;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        boolean isTestAnnotationPresent = method.isAnnotationPresent(DTest.class);
        boolean isTestConfigAnnotationPresent = method.isAnnotationPresent(DTestConfig.class);
        if (isTestAnnotationPresent || isTestConfigAnnotationPresent) {
            String methodName = getMethodName(invocation);
            LOG.info("---------- Run automation testcase :: {}() ----------", methodName);

            Object object = null;
            if (isTestAnnotationPresent) {
                object = invocation.proceed();
            } else {
                DTestConfig testConfigAnnotation = method.getAnnotation(DTestConfig.class);
                String group = convertSpel(invocation, testConfigAnnotation.group());
                String serviceId = convertSpel(invocation, testConfigAnnotation.serviceId());
                String prefix = convertSpel(invocation, testConfigAnnotation.prefix());
                String suffix = convertSpel(invocation, testConfigAnnotation.suffix());
                String executePath = convertSpel(invocation, testConfigAnnotation.executePath());
                String resetPath = convertSpel(invocation, testConfigAnnotation.resetPath());

                if (StringUtils.isNotEmpty(prefix)) {
                    group = prefix + "-" + group;
                }
                if (StringUtils.isNotEmpty(suffix)) {
                    serviceId = serviceId + "-" + suffix;
                }

                testOperation.update(group, serviceId, executePath);

                Thread.sleep(configOperationAwaitTime);

                try {
                    object = invocation.proceed();
                } finally {
                    if (StringUtils.isNotEmpty(resetPath)) {
                        testOperation.update(group, serviceId, resetPath);
                    } else {
                        testOperation.clear(group, serviceId);
                    }

                    Thread.sleep(configOperationAwaitTime);
                }
            }

            LOG.info("* Passed");

            return object;
        }

        return invocation.proceed();
    }

    private String convertSpel(MethodInvocation invocation, String key) {
        String spelKey = null;
        try {
            spelKey = getSpelKey(invocation, key);
        } catch (Exception e) {
            spelKey = key;
        }

        return spelKey;
    }
}