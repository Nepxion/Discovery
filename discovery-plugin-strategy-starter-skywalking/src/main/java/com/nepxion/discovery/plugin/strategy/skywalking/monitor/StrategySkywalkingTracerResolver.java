package com.nepxion.discovery.plugin.strategy.skywalking.monitor;

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

import com.esotericsoftware.reflectasm.MethodAccess;
import com.nepxion.discovery.common.constant.DiscoveryConstant;

// 通过高性能reflectasm字节码反射框架来反射获取Skywalking的TraceId，不支持反射获取Skywalking的SpanId
// 1. 由于Skywalking的ContextManager类未暴露出公共的获取SpanId的方法，reflectasm字节码反射不支持调用私有方法和属性
// 2. 虽然可以通过method.setAccessible(true)方式访问私有方法和属性，但需要走Java原生反射方式，会较耗性能
// 3. SpanId值以N/A值为代替，等Skywalking官方支持后，再行修改
public class StrategySkywalkingTracerResolver {
    private static final Logger LOG = LoggerFactory.getLogger(StrategySkywalkingTracerResolver.class);

    private static MethodAccess contextManagerMethodAccess;
    private static int getGlobalTraceIdMethodIndex;

    static {
        try {
            contextManagerMethodAccess = MethodAccess.get(Class.forName("org.apache.skywalking.apm.agent.core.context.ContextManager"));
            getGlobalTraceIdMethodIndex = contextManagerMethodAccess.getIndex("getGlobalTraceId");
        } catch (Exception e) {
            LOG.error("Initialize Skywalking ContextManager MethodAccesses failed", e);
        }
    }

    public static String getTraceId() {
        if (System.getProperties().get("skywalking.agent.service_name") == null) {
            return null;
        }

        if (contextManagerMethodAccess == null) {
            return null;
        }

        return contextManagerMethodAccess.invoke(null, getGlobalTraceIdMethodIndex).toString();
    }

    public static String getSpanId() {
        return DiscoveryConstant.NA;
    }
}