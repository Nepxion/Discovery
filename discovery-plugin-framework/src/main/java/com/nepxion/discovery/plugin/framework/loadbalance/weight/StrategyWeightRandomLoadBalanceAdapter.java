package com.nepxion.discovery.plugin.framework.loadbalance.weight;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.nepxion.discovery.common.entity.RegionWeightEntity;
import com.nepxion.discovery.common.entity.VersionWeightEntity;
import com.nepxion.discovery.common.entity.WeightEntity;
import com.nepxion.discovery.common.entity.WeightEntityWrapper;
import com.nepxion.discovery.common.entity.WeightFilterEntity;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginContextHolder;
import com.netflix.loadbalancer.Server;

public class StrategyWeightRandomLoadBalanceAdapter extends AbstractWeightRandomLoadBalanceAdapter<WeightFilterEntity> {
    public StrategyWeightRandomLoadBalanceAdapter(PluginAdapter pluginAdapter, PluginContextHolder pluginContextHolder) {
        super(pluginAdapter, pluginContextHolder);
    }

    @Override
    public WeightFilterEntity getT() {
        if (pluginContextHolder == null) {
            return null;
        }

        WeightFilterEntity weightFilterEntity = new WeightFilterEntity();

        String versionWeightValue = pluginContextHolder.getContextRouteVersionWeight();
        if (StringUtils.isNotEmpty(versionWeightValue)) {
            try {
                List<WeightEntity> weightEntityList = WeightEntityWrapper.parseWeightEntityList(versionWeightValue);
                weightFilterEntity.setVersionWeightEntityList(weightEntityList);
            } catch (Exception e) {
                VersionWeightEntity weightEntity = new VersionWeightEntity();

                WeightEntityWrapper.parseWeightEntity(weightEntity, versionWeightValue);

                weightFilterEntity.setVersionWeightEntity(weightEntity);
            }
        }

        String regionWeightValue = pluginContextHolder.getContextRouteRegionWeight();
        if (StringUtils.isNotEmpty(regionWeightValue)) {
            try {
                List<WeightEntity> weightEntityList = WeightEntityWrapper.parseWeightEntityList(regionWeightValue);
                weightFilterEntity.setRegionWeightEntityList(weightEntityList);
            } catch (Exception e) {
                RegionWeightEntity weightEntity = new RegionWeightEntity();

                WeightEntityWrapper.parseWeightEntity(weightEntity, regionWeightValue);

                weightFilterEntity.setRegionWeightEntity(weightEntity);
            }
        }

        return weightFilterEntity;
    }

    @Override
    public int getWeight(Server server, WeightFilterEntity weightFilterEntity) {
        String providerServiceId = pluginAdapter.getServerServiceId(server);
        String providerVersion = pluginAdapter.getServerVersion(server);
        String providerRegion = pluginAdapter.getServerRegion(server);

        return WeightEntityWrapper.getWeight(weightFilterEntity, providerServiceId, providerVersion, providerRegion, null);
    }
}