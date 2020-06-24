package com.nepxion.discovery.plugin.strategy.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

public class DefaultStrategyVersionFilterAdapter implements StrategyVersionFilterAdapter {
    @Override
    public List<String> filter(List<String> versionList) {
        String version = versionList.get(0);
        versionList.clear();
        versionList.add(version);

        return versionList;
    }
}