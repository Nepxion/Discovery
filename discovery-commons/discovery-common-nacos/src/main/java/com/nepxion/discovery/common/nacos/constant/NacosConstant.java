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
    public static final String NACOS_PREFIX = "nacos";
    public static final String NACOS_TIMEOUT = "nacos.timout";

    public static final String SPRING_CLOUD_NACOS_CONFIG_PREFIX = "spring.cloud.nacos.config";
    public static final String SPRING_CLOUD_NACOS_CONFIG_TIMEOUT = "spring.cloud.nacos.config.timout";

    public static final long NACOS_DEFAULT_TIMEOUT = 30000;
}