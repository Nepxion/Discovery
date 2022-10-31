package com.nepxion.discovery.plugin.strategy.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.WareRequestSource;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginContextHolder;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;
import com.nepxion.discovery.plugin.strategy.matcher.DiscoveryMatcher;
import com.netflix.loadbalancer.Server;

public abstract class AbstractStrategyEnabledFilter implements StrategyEnabledFilter {
    @Autowired
    protected DiscoveryMatcher discoveryMatcher;

    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected PluginContextHolder pluginContextHolder;

    @Autowired
    protected StrategyContextHolder strategyContextHolder;

    @Override
    public void filter(List<? extends Server> servers) {
        Iterator<? extends Server> iterator = servers.iterator();
        while (iterator.hasNext()) {
            Server server = iterator.next();

            boolean enabled = apply(servers, server);
            if (!enabled) {
                iterator.remove();
            }
        }
    }

    public boolean findByGroup(List<? extends Server> servers, String group) {
        for (Server server : servers) {
            String serverGroup = pluginAdapter.getServerGroup(server);
            if (StringUtils.equals(group, serverGroup)) {
                return true;
            }
        }

        return false;
    }

    public boolean findByVersion(List<? extends Server> servers, String version) {
        for (Server server : servers) {
            String serverVersion = pluginAdapter.getServerVersion(server);
            if (StringUtils.equals(version, serverVersion)) {
                return true;
            }
        }

        return false;
    }

    public boolean findByRegion(List<? extends Server> servers, String region) {
        for (Server server : servers) {
            String serverRegion = pluginAdapter.getServerRegion(server);
            if (StringUtils.equals(region, serverRegion)) {
                return true;
            }
        }

        return false;
    }

    public boolean findByEnvironment(List<? extends Server> servers, String environment) {
        for (Server server : servers) {
            String serverEnvironment = pluginAdapter.getServerEnvironment(server);
            if (StringUtils.equals(environment, serverEnvironment)) {
                return true;
            }
        }

        return false;
    }

    public boolean findByZone(List<? extends Server> servers, String zone) {
        for (Server server : servers) {
            String serverZone = pluginAdapter.getServerZone(server);
            if (StringUtils.equals(zone, serverZone)) {
                return true;
            }
        }

        return false;
    }

    public boolean matchByVersion(List<? extends Server> servers, String versions) {
        for (Server server : servers) {
            String serverVersion = pluginAdapter.getServerVersion(server);
            if (discoveryMatcher.match(versions, serverVersion, true)) {
                return true;
            }
        }

        return false;
    }

    public boolean matchByRegion(List<? extends Server> servers, String regions) {
        for (Server server : servers) {
            String serverRegion = pluginAdapter.getServerRegion(server);
            if (discoveryMatcher.match(regions, serverRegion, true)) {
                return true;
            }
        }

        return false;
    }

    public boolean matchByAddress(List<? extends Server> servers, String addresses) {
        for (Server server : servers) {
            String serverHost = server.getHost();
            int serverPort = server.getPort();
            if (discoveryMatcher.matchAddress(addresses, serverHost, serverPort, true)) {
                return true;
            }
        }

        return false;
    }

    public List<String> assembleVersionList(List<? extends Server> servers) {
        List<String> versionList = new ArrayList<String>();
        for (Server server : servers) {
            String serverVersion = pluginAdapter.getServerVersion(server);
            versionList.add(serverVersion);
        }

        if (versionList.size() > 1) {
            Collections.sort(versionList);
        }

        return versionList;
    }

    public boolean isWareRequestFailoverEnabled() {
        String requestSource = strategyContextHolder.getHeader(DiscoveryConstant.N_DW_REQUEST_SOURCE);
        if (StringUtils.isEmpty(requestSource)) {
            return true;
        }

        WareRequestSource wareRequestSource = null;
        try {
            wareRequestSource = WareRequestSource.fromString(requestSource);
        } catch (Exception e) {
            return true;
        }

        switch (wareRequestSource) {
            case ROCKETMQ:
                return false;
        }

        return true;
    }

    public DiscoveryMatcher getDiscoveryMatcher() {
        return discoveryMatcher;
    }

    public PluginAdapter getPluginAdapter() {
        return pluginAdapter;
    }

    public PluginContextHolder getPluginContextHolder() {
        return pluginContextHolder;
    }
}