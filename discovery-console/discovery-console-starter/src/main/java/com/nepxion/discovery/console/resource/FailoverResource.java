package com.nepxion.discovery.console.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.common.entity.FailoverType;

public interface FailoverResource {
    String createFailover(String group, FailoverType failoverType, String failoverValue);

    String clearFailover(String group, FailoverType failoverType);

    String createFailover(String group, String gatewayId, FailoverType failoverType, String failoverValue);

    String clearFailover(String group, String gatewayId, FailoverType failoverType);
}