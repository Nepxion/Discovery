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
    // 是否要环境切流
    boolean isTransferred();

    // 切流到哪个环境中
    String getTransferredEnvironment();
}