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
    public static final String TYPE = "Nacos";
    public static final String NACOS_SERVER_ADDR = "nacos.server-addr";
    public static final String NACOS_DISCOVERY_NAMESPACE = "nacos.discovery.namespace";
    public static final String NACOS_DISCOVERY_TIMEOUT = "nacos.discovery.timout";

    public static final long DEFAULT_TIMEOUT = 30000;
}