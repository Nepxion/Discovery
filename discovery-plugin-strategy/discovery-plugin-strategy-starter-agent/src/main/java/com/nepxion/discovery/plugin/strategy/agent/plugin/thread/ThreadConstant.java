package com.nepxion.discovery.plugin.strategy.agent.plugin.thread;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import com.nepxion.discovery.plugin.strategy.agent.plugin.thread.interceptor.ThreadCallInterceptor;
import com.nepxion.discovery.plugin.strategy.agent.plugin.thread.interceptor.ThreadConstructorInterceptor;

public class ThreadConstant {
    public static final String THREAD_SCAN_PACKAGES = "thread.scan.packages";
    public static final String THREAD_REQUEST_DECORATOR_ENABLED = "thread.request.decorator.enabled";
    public static final String RUNNABLE_CLASS_NAME = "java.lang.Runnable";
    public static final String CALLABLE_CLASS_NAME = "java.util.concurrent.Callable";

    public static final String THREAD_SCAN_PACKAGES_DELIMITERS = ";";
    public static final String CONSTRUCTOR_INTERCEPTOR = String.format("%s.before(this);\n", ThreadConstructorInterceptor.class.getName());
    public static final String RUN_BEFORE_INTERCEPTOR = String.format("%s.before(this);\n", ThreadCallInterceptor.class.getName());
    public static final String RUN_AFTER_INTERCEPTOR = String.format("%s.after(this);\n", ThreadCallInterceptor.class.getName());
}