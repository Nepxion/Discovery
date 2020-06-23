package com.nepxion.discovery.plugin.strategy.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

public class DefaultStrategyVersionFilterAdapter implements StrategyVersionFilterAdapter {
    @Override
    public List<String> filter(List<String> versionList) {
        List<String> filterVersionList = new ArrayList<String>();
        if (CollectionUtils.isNotEmpty(versionList)) {
            filterVersionList.add(versionList.get(0));
        }

        return filterVersionList;
    }
}