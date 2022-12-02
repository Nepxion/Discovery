package com.nepxion.discovery.common.lock;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

public interface DiscoveryLock {
    // 尝试获取锁
    boolean tryLock(String key);

    // 持有锁
    void lock(String key);

    // 释放锁
    void unlock(String key);

    // 获取被持有的所有锁名称列表
    List<String> getHeldLocks();

    // 进程结束时销毁锁
    void destroy() throws Exception;
}