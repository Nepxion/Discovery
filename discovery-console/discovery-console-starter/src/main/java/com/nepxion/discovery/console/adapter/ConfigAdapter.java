package com.nepxion.discovery.console.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.common.entity.ConfigType;
import com.nepxion.discovery.common.entity.FormatType;

public interface ConfigAdapter {
    boolean updateConfig(String group, String serviceId, String config) throws Exception;

    // 除了Nacos，其它配置中心不需要FormatType
    default boolean updateConfig(String group, String serviceId, String config, FormatType formatType) throws Exception {
        return updateConfig(group, serviceId, config);
    }

    boolean clearConfig(String group, String serviceId) throws Exception;

    String getConfig(String group, String serviceId) throws Exception;

    ConfigType getConfigType();
}