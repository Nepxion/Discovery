package com.nepxion.discovery.plugin.strategy.gateway.route;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Ning Zhang
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import com.nepxion.discovery.plugin.strategy.gateway.entity.GatewayStrategyRouteEntity;

// 只给数据库存储用
public class PlatformGatewayStrategyRoute extends AbstractGatewayStrategyRoute {
    @PostConstruct
    public void initialize() {
        // 从数据库里获取动态路由列表
        List<GatewayStrategyRouteEntity> gatewayStrategyRouteEntityList = new ArrayList<>();
        updateAll(gatewayStrategyRouteEntityList);
    }
}