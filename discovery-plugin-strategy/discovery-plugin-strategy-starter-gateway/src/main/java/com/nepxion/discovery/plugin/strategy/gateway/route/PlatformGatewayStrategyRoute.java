package com.nepxion.discovery.plugin.strategy.gateway.route;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Ning Zhang
 * @version 1.0
 */

import com.nepxion.discovery.plugin.strategy.gateway.entity.GatewayStrategyRouteEntity;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

// 从数据库、配置中心或其他地方初始化路由用
public class PlatformGatewayStrategyRoute extends AbstractGatewayStrategyRoute {

    @PostConstruct
    public void initialize() {
        // 从数据库、配置中或其他地方获取动态路由列表
        List<GatewayStrategyRouteEntity> gatewayStrategyRouteEntityList = new ArrayList<>();
        updateAll(gatewayStrategyRouteEntityList);
    }
}