package com.nepxion.discovery.plugin.hystrix.context;

import java.util.concurrent.Callable;

public interface HystrixCallableWrapper {

    /**
     * 对callable进行包装
     * @param delegate
     * @param <T>
     * @return
     */
    <T> Callable<T> wrapCallable(Callable<T> delegate);

}
