package com.nepxion.discovery.console.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.entity.FailoverType;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.StrategyFailoverEntity;
import com.nepxion.discovery.console.delegate.ConsoleResourceDelegateImpl;

public class FailoverResourceImpl extends ConsoleResourceDelegateImpl implements FailoverResource {
    @Autowired
    private ConfigResource configResource;

    @Override
    public String createFailover(FailoverType failoverType, String group, String failoverValue) {
        return createFailover(failoverType, group, null, failoverValue);
    }

    @Override
    public String clearFailover(FailoverType failoverType, String group) {
        return clearFailover(failoverType, group, null);
    }

    @Override
    public String createFailover(FailoverType failoverType, String group, String serviceId, String failoverValue) {
        RuleEntity ruleEntity = getRemoteRuleEntity(group, serviceId);

        createFailover(failoverType, ruleEntity, failoverValue);

        updateRemoteRuleEntity(group, serviceId, ruleEntity);

        return configResource.fromRuleEntity(ruleEntity);
    }

    @Override
    public String clearFailover(FailoverType failoverType, String group, String serviceId) {
        RuleEntity ruleEntity = getRemoteRuleEntity(group, serviceId);

        clearFailover(failoverType, ruleEntity);

        updateRemoteRuleEntity(group, serviceId, ruleEntity);

        return configResource.fromRuleEntity(ruleEntity);
    }

    private void createFailover(FailoverType failoverType, RuleEntity ruleEntity, String failoverValue) {
        StrategyFailoverEntity strategyFailoverEntity = ruleEntity.getStrategyFailoverEntity();
        if (strategyFailoverEntity == null) {
            strategyFailoverEntity = new StrategyFailoverEntity();
            ruleEntity.setStrategyFailoverEntity(strategyFailoverEntity);
        }

        setFailover(failoverType, strategyFailoverEntity, failoverValue);
    }

    private void clearFailover(FailoverType failoverType, RuleEntity ruleEntity) {
        StrategyFailoverEntity strategyFailoverEntity = ruleEntity.getStrategyFailoverEntity();
        if (strategyFailoverEntity != null) {
            setFailover(failoverType, strategyFailoverEntity, null);
        }
    }

    private void setFailover(FailoverType failoverType, StrategyFailoverEntity strategyFailoverEntity, String failoverValue) {
        switch (failoverType) {
            case VERSION_PREFER:
                strategyFailoverEntity.setVersionPreferValue(failoverValue);
                break;
            case VERSION_FAILOVER:
                strategyFailoverEntity.setVersionFailoverValue(failoverValue);
                break;
            case REGION_TRANSFER:
                strategyFailoverEntity.setRegionTransferValue(failoverValue);
                break;
            case REGION_FAILOVER:
                strategyFailoverEntity.setRegionFailoverValue(failoverValue);
                break;
            case ENVIRONMENT_FAILOVER:
                strategyFailoverEntity.setEnvironmentFailoverValue(failoverValue);
                break;
            case ZONE_FAILOVER:
                strategyFailoverEntity.setZoneFailoverValue(failoverValue);
                break;
            case ADDRESS_FAILOVER:
                strategyFailoverEntity.setAddressFailoverValue(failoverValue);
                break;
        }
    }
}