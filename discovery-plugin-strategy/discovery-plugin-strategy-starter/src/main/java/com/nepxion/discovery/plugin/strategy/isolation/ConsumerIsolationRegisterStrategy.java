package com.nepxion.discovery.plugin.strategy.isolation;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.client.serviceregistry.Registration;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.plugin.framework.event.RegisterFailureEvent;
import com.nepxion.discovery.plugin.framework.listener.register.AbstractRegisterListener;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;

// 当本服务的元数据中的Group在黑名单里或者不在白名单里，禁止被注册到注册中心
public class ConsumerIsolationRegisterStrategy extends AbstractRegisterListener {
    @Override
    public void onRegister(Registration registration) {
        String serviceId = pluginAdapter.getServiceId();
        String host = pluginAdapter.getHost();
        int port = pluginAdapter.getPort();
        String group = pluginAdapter.getGroup();

        List<String> groupBlacklist = getGroupBlacklist();
        if (CollectionUtils.isNotEmpty(groupBlacklist) && groupBlacklist.contains(group)) {
            onRegisterFailure(group, serviceId, host, port, true);
        }

        List<String> groupWhitelist = getGroupWhitelist();
        if (CollectionUtils.isNotEmpty(groupWhitelist) && !groupWhitelist.contains(group)) {
            onRegisterFailure(group, serviceId, host, port, false);
        }
    }

    protected List<String> getGroupBlacklist() {
        String groupBlacklistText = pluginContextAware.getEnvironment().getProperty(StrategyConstant.SPRING_APPLICATION_STRATEGY_REGISTER_ISOLATION_GROUP_BLACKLIST, String.class, null);
        if (StringUtils.isEmpty(groupBlacklistText)) {
            return null;
        }

        return StringUtil.splitToList(groupBlacklistText);
    }

    protected List<String> getGroupWhitelist() {
        String groupWhitelistText = pluginContextAware.getEnvironment().getProperty(StrategyConstant.SPRING_APPLICATION_STRATEGY_REGISTER_ISOLATION_GROUP_WHITELIST, String.class, null);
        if (StringUtils.isEmpty(groupWhitelistText)) {
            return null;
        }

        return StringUtil.splitToList(groupWhitelistText);
    }

    private void onRegisterFailure(String group, String serviceId, String host, int port, boolean isBlacklist) {
        String description = serviceId + " for " + host + ":" + port + " is rejected to register to Register server, it's group=" + group;
        if (isBlacklist) {
            description += " is in isolation blacklist";
        } else {
            description += " isn't in isolation whitelist";
        }

        pluginEventWapper.fireRegisterFailure(new RegisterFailureEvent(DiscoveryConstant.REGISTER_ISOLATION, description, serviceId, host, port));

        throw new DiscoveryException(description);
    }

    @Override
    public void onDeregister(Registration registration) {

    }

    @Override
    public void onSetStatus(Registration registration, String status) {

    }

    @Override
    public void onClose() {

    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}