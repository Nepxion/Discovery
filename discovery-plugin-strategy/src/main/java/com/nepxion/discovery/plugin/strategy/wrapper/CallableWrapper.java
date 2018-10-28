package com.nepxion.discovery.plugin.strategy.wrapper;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Hao Wang
 * @version 1.0
 */

import java.util.concurrent.Callable;

public interface CallableWrapper {
    <T> Callable<T> wrapCallable(Callable<T> delegate);
}