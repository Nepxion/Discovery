package com.nepxion.discovery.plugin.framework.loadbalance;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import com.nepxion.discovery.common.entity.WeightFilterEntity;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.netflix.loadbalancer.Server;

public interface IWeightRandomLoadBalance {
    void setPluginAdapter(PluginAdapter pluginAdapter);

    WeightFilterEntity getWeightFilterEntity();

    Server choose(List<Server> serverList, WeightFilterEntity weightFilterEntity);
}