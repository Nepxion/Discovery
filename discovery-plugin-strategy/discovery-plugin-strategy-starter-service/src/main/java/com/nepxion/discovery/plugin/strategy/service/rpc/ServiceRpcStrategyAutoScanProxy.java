package com.nepxion.discovery.plugin.strategy.service.rpc;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.lang.annotation.Annotation;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nepxion.discovery.plugin.strategy.service.annotation.ServiceStrategy;
import com.nepxion.matrix.proxy.aop.DefaultAutoScanProxy;
import com.nepxion.matrix.proxy.mode.ProxyMode;
import com.nepxion.matrix.proxy.mode.ScanMode;

public class ServiceRpcStrategyAutoScanProxy extends DefaultAutoScanProxy {
    private static final long serialVersionUID = 8436914718400274011L;

    private String[] commonInterceptorNames;

    @SuppressWarnings("rawtypes")
    private Class[] classAnnotations;

    @SuppressWarnings("rawtypes")
    private Class[] methodAnnotations;

    public ServiceRpcStrategyAutoScanProxy(String scanPackages) {
        super(scanPackages, ProxyMode.BY_CLASS_OR_METHOD_ANNOTATION, ScanMode.FOR_CLASS_OR_METHOD_ANNOTATION);
    }

    @Override
    protected String[] getCommonInterceptorNames() {
        if (commonInterceptorNames == null) {
            commonInterceptorNames = new String[] { "serviceRpcStrategyInterceptor" };
        }

        return commonInterceptorNames;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Class<? extends Annotation>[] getClassAnnotations() {
        if (classAnnotations == null) {
            classAnnotations = new Class[] { ServiceStrategy.class };
        }

        return classAnnotations;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Class<? extends Annotation>[] getMethodAnnotations() {
        if (methodAnnotations == null) {
            methodAnnotations = new Class[] { RequestMapping.class, GetMapping.class, PostMapping.class, PutMapping.class, DeleteMapping.class, PatchMapping.class };
        }

        return methodAnnotations;
    }
}