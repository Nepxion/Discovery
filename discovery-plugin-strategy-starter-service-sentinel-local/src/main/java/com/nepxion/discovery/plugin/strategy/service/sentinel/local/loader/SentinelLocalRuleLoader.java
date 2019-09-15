package com.nepxion.discovery.plugin.strategy.service.sentinel.local.loader;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Weihua Wang
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.plugin.strategy.service.sentinel.loader.AbstractSentinelRuleLoader;

public class SentinelLocalRuleLoader extends AbstractSentinelRuleLoader {

    @Override
    public void load() {
        loadLocal();
        loadLog();
    }
}