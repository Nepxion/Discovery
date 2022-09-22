package com.nepxion.discovery.console.delegate;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.console.resource.ConfigResource;

public class ConsoleResourceDelegateImpl implements ConsoleResourceDelegate {
    private static final Logger LOG = LoggerFactory.getLogger(ConsoleResourceDelegateImpl.class);

    @Autowired
    private ConfigResource configResource;

    @Override
    public RuleEntity getRemoteRuleEntity(String group, String serviceId) {
        String subscriptionServiceId = StringUtils.isEmpty(serviceId) ? group : serviceId;

        RuleEntity ruleEntity = null;
        try {
            ruleEntity = configResource.getRemoteRuleEntity(group, subscriptionServiceId);
        } catch (Exception e) {
            ruleEntity = new RuleEntity();

            LOG.warn("Get remote RuleEntity failed, group={}, serviceId={}", group, subscriptionServiceId, e);
        }

        return ruleEntity;
    }

    @Override
    public boolean updateRemoteRuleEntity(String group, String serviceId, RuleEntity ruleEntity) {
        String subscriptionServiceId = StringUtils.isEmpty(serviceId) ? group : serviceId;

        try {
            return configResource.updateRemoteRuleEntity(group, subscriptionServiceId, ruleEntity);
        } catch (Exception e) {
            throw new DiscoveryException("Update remote RuleEntity failed, group=" + group + ", serviceId=" + subscriptionServiceId, e);
        }
    }
}