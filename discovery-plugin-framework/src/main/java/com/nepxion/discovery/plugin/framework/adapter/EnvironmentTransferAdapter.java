package com.nepxion.discovery.plugin.framework.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

public interface EnvironmentTransferAdapter {
    // 是否要环境路由
    boolean isTransferred();

    // 路由到哪个环境中
    String getTransferredEnvironment();
}