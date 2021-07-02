package com.nepxion.discovery.common.future;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class DiscoveryFutureResolver {
    public static <T> T call(ExecutorService executorService, DiscoveryFutureCallback<T> discoveryFutureCallback) throws InterruptedException, ExecutionException {
        Future<T> future = executorService.submit(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return discoveryFutureCallback.callback();
            }
        });

        return future.get();
    }
}