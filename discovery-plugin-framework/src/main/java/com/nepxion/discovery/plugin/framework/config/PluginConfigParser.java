package com.nepxion.discovery.plugin.framework.config;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.InputStream;

import com.nepxion.discovery.plugin.framework.entity.RuleEntity;

public interface PluginConfigParser {
    RuleEntity parse(InputStream inputStream);
}