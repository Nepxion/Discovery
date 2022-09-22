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
    public String createFailover(String group, FailoverType failoverType, String failoverValue) {
        return createFailover(group, null, failoverType, failoverValue);
    }

    @Override
    public String clearFailover(String group, FailoverType failoverType) {
        return clearFailover(group, null, failoverType);
    }

    @Override
    public String createFailover(String group, String gatewayId, FailoverType failoverType, String failoverValue) {
        RuleEntity ruleEntity = getRemoteRuleEntity(group, gatewayId);

        createFailover(ruleEntity, failoverType, failoverValue);

        updateRemoteRuleEntity(group, gatewayId, ruleEntity);

        return configResource.deparse(ruleEntity);
    }

    @Override
    public String clearFailover(String group, String gatewayId, FailoverType failoverType) {
        RuleEntity ruleEntity = getRemoteRuleEntity(group, gatewayId);

        clearFailover(ruleEntity, failoverType);

        updateRemoteRuleEntity(group, gatewayId, ruleEntity);

        return configResource.deparse(ruleEntity);
    }

    private void createFailover(RuleEntity ruleEntity, FailoverType failoverType, String failoverValue) {
        StrategyFailoverEntity strategyFailoverEntity = ruleEntity.getStrategyFailoverEntity();
        if (strategyFailoverEntity == null) {
            strategyFailoverEntity = new StrategyFailoverEntity();
            ruleEntity.setStrategyFailoverEntity(strategyFailoverEntity);
        }

        setFailover(strategyFailoverEntity, failoverType, failoverValue);
    }

    private void clearFailover(RuleEntity ruleEntity, FailoverType failoverType) {
        StrategyFailoverEntity strategyFailoverEntity = ruleEntity.getStrategyFailoverEntity();
        if (strategyFailoverEntity != null) {
            setFailover(strategyFailoverEntity, failoverType, null);
        }
    }

    private void setFailover(StrategyFailoverEntity strategyFailoverEntity, FailoverType failoverType, String failoverValue) {
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