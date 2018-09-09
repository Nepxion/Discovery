package com.nepxion.discovery.common.nacos.constant;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.alibaba.nacos.api.PropertyKeyConst;

public class NacosConstant extends PropertyKeyConst {
    public static final String URL = "nacos.url";
    public static final String NAMESPACE = "nacos.discovery.namespace";
    public static final String TIMEOUT = "nacos.discovery.timout";

    public static final long DEFAULT_TIMEOUT = 30000;
}