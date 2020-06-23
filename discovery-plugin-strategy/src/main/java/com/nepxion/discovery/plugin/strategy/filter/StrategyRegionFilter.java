package com.nepxion.discovery.plugin.strategy.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.adapter.StrategyRegionFilterAdapter;
import com.netflix.loadbalancer.Server;

public class StrategyRegionFilter extends AbstractStrategyFilter {
    @Autowired
    private StrategyRegionFilterAdapter strategyRegionFilterAdapter;

    @Override
    public String getMetadataKey() {
        return DiscoveryConstant.REGION;
    }

    @Override
    public List<String> filterMetadataValueList(List<String> metadataValueList) {
        return strategyRegionFilterAdapter.filter(metadataValueList);
    }

    @Override
    public String getMetadataValue(Server server) {
        return pluginAdapter.getServerRegion(server);
    }
}