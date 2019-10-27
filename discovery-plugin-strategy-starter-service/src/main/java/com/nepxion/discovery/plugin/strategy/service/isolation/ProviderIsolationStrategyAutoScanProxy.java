package com.nepxion.discovery.plugin.strategy.service.isolation;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.lang.annotation.Annotation;

import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.plugin.strategy.service.annotation.ServiceStrategy;
import com.nepxion.matrix.proxy.aop.DefaultAutoScanProxy;
import com.nepxion.matrix.proxy.mode.ProxyMode;
import com.nepxion.matrix.proxy.mode.ScanMode;

public class ProviderIsolationStrategyAutoScanProxy extends DefaultAutoScanProxy {
    private static final long serialVersionUID = 6147822053647878553L;

    private String[] commonInterceptorNames;

    @SuppressWarnings("rawtypes")
    private Class[] classAnnotations;

    public ProviderIsolationStrategyAutoScanProxy(String scanPackages) {
        super(scanPackages, ProxyMode.BY_CLASS_ANNOTATION_ONLY, ScanMode.FOR_CLASS_ANNOTATION_ONLY);
    }

    @Override
    protected String[] getCommonInterceptorNames() {
        if (commonInterceptorNames == null) {
            commonInterceptorNames = new String[] { "providerIsolationStrategyInterceptor" };
        }

        return commonInterceptorNames;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Class<? extends Annotation>[] getClassAnnotations() {
        if (classAnnotations == null) {
            classAnnotations = new Class[] { RestController.class, ServiceStrategy.class };
        }

        return classAnnotations;
    }
}