package com.nepxion.discovery.plugin.strategy.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Xun Zhong
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

public interface StrategyMonitorPackagesInjector {
    List<String> getScanPackages();
}