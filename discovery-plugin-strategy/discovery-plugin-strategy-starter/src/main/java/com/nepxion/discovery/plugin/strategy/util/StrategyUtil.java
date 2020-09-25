package com.nepxion.discovery.plugin.strategy.util;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;

import com.nepxion.discovery.common.constant.DiscoveryConstant;

public class StrategyUtil {
    public static boolean isCoreHeaderContains(String headerName) {
        return StringUtils.equals(headerName, DiscoveryConstant.N_D_VERSION) ||
                StringUtils.equals(headerName, DiscoveryConstant.N_D_REGION) ||
                StringUtils.equals(headerName, DiscoveryConstant.N_D_ADDRESS) ||
                StringUtils.equals(headerName, DiscoveryConstant.N_D_VERSION_WEIGHT) ||
                StringUtils.equals(headerName, DiscoveryConstant.N_D_REGION_WEIGHT) ||
                StringUtils.equals(headerName, DiscoveryConstant.N_D_ID_BLACKLIST) ||
                StringUtils.equals(headerName, DiscoveryConstant.N_D_ADDRESS_BLACKLIST);
    }

    public static boolean isInnerHeaderContains(String headerName) {
        return StringUtils.equals(headerName, DiscoveryConstant.N_D_SERVICE_GROUP) ||
                StringUtils.equals(headerName, DiscoveryConstant.N_D_SERVICE_TYPE) ||
                StringUtils.equals(headerName, DiscoveryConstant.N_D_SERVICE_APP_ID) ||
                StringUtils.equals(headerName, DiscoveryConstant.N_D_SERVICE_ID) ||
                StringUtils.equals(headerName, DiscoveryConstant.N_D_SERVICE_ADDRESS) ||
                StringUtils.equals(headerName, DiscoveryConstant.N_D_SERVICE_VERSION) ||
                StringUtils.equals(headerName, DiscoveryConstant.N_D_SERVICE_REGION) ||
                StringUtils.equals(headerName, DiscoveryConstant.N_D_SERVICE_ENVIRONMENT) ||
                StringUtils.equals(headerName, DiscoveryConstant.N_D_SERVICE_ZONE);
    }
}
