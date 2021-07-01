package com.nepxion.discovery.common.thread;

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

public abstract class DiscoveryBlockFuture<T> {
    private ExecutorService executorService;

    public DiscoveryBlockFuture(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public T call() throws InterruptedException, ExecutionException {
        Future<T> future = executorService.submit(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return onCall();
            }
        });

        return future.get();
    }

    public abstract T onCall();
}