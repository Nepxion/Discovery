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
    String createFailover(FailoverType failoverType, String group, String failoverValue);

    String clearFailover(FailoverType failoverType, String group);

    String createFailover(FailoverType failoverType, String group, String serviceId, String failoverValue);

    String clearFailover(FailoverType failoverType, String group, String serviceId);
}