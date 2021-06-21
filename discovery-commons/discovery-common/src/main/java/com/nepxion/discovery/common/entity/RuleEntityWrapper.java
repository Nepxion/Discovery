package com.nepxion.discovery.common.entity;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

public class RuleEntityWrapper {
    // 以局部规则为准，如果局部规则规则里某些属性没有，而全局规则有，则从全局规则把那些属性复制到局部规则，并合并创建最终的复合规则
    public static RuleEntity assemble(RuleEntity partialRuleEntity, RuleEntity globalRuleEntity) {
        RuleEntity ruleEntity = new RuleEntity();

        RegisterEntity registerEntity = null;
        if (partialRuleEntity != null && partialRuleEntity.getRegisterEntity() != null) {
            registerEntity = partialRuleEntity.getRegisterEntity();
        } else if (globalRuleEntity != null && globalRuleEntity.getRegisterEntity() != null) {
            registerEntity = globalRuleEntity.getRegisterEntity();
        }
        ruleEntity.setRegisterEntity(registerEntity);

        DiscoveryEntity discoveryEntity = null;
        if (partialRuleEntity != null && partialRuleEntity.getDiscoveryEntity() != null) {
            discoveryEntity = partialRuleEntity.getDiscoveryEntity();
        } else if (globalRuleEntity != null && globalRuleEntity.getDiscoveryEntity() != null) {
            discoveryEntity = globalRuleEntity.getDiscoveryEntity();
        }
        ruleEntity.setDiscoveryEntity(discoveryEntity);

        StrategyEntity strategyEntity = null;
        if (partialRuleEntity != null && partialRuleEntity.getStrategyEntity() != null) {
            strategyEntity = partialRuleEntity.getStrategyEntity();
        } else if (globalRuleEntity != null && globalRuleEntity.getStrategyEntity() != null) {
            strategyEntity = globalRuleEntity.getStrategyEntity();
        }
        ruleEntity.setStrategyEntity(strategyEntity);

        StrategyReleaseEntity strategyReleaseEntity = null;
        if (partialRuleEntity != null && partialRuleEntity.getStrategyReleaseEntity() != null) {
            strategyReleaseEntity = partialRuleEntity.getStrategyReleaseEntity();
        } else if (globalRuleEntity != null && globalRuleEntity.getStrategyReleaseEntity() != null) {
            strategyReleaseEntity = globalRuleEntity.getStrategyReleaseEntity();
        }
        ruleEntity.setStrategyReleaseEntity(strategyReleaseEntity);

        StrategyBlacklistEntity strategyBlacklistEntity = null;
        if (partialRuleEntity != null && partialRuleEntity.getStrategyBlacklistEntity() != null) {
            strategyBlacklistEntity = partialRuleEntity.getStrategyBlacklistEntity();
        } else if (globalRuleEntity != null && globalRuleEntity.getStrategyBlacklistEntity() != null) {
            strategyBlacklistEntity = globalRuleEntity.getStrategyBlacklistEntity();
        }
        ruleEntity.setStrategyBlacklistEntity(strategyBlacklistEntity);

        ParameterEntity parameterEntity = null;
        if (partialRuleEntity != null && partialRuleEntity.getParameterEntity() != null) {
            parameterEntity = partialRuleEntity.getParameterEntity();
        } else if (globalRuleEntity != null && globalRuleEntity.getParameterEntity() != null) {
            parameterEntity = globalRuleEntity.getParameterEntity();
        }
        ruleEntity.setParameterEntity(parameterEntity);

        String content = null;
        if (partialRuleEntity != null && partialRuleEntity.getContent() != null) {
            content = partialRuleEntity.getContent();
        } else if (globalRuleEntity != null && globalRuleEntity.getContent() != null) {
            content = globalRuleEntity.getContent();
        }
        ruleEntity.setContent(content);

        return ruleEntity;
    }
}