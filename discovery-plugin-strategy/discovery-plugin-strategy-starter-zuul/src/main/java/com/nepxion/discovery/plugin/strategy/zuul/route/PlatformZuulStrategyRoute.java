package com.nepxion.discovery.plugin.strategy.zuul.route;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Ning Zhang
 * @version 1.0
 */

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

import com.nepxion.discovery.plugin.strategy.zuul.entity.ZuulStrategyRouteEntity;

// 只给数据库存储用
public class PlatformZuulStrategyRoute extends AbstractZuulStrategyRoute {
    public PlatformZuulStrategyRoute(String servletPath, ZuulProperties zuulProperties) {
        super(servletPath, zuulProperties);
    }

    @PostConstruct
    public void initialize() {
        Map<String, ZuulStrategyRouteEntity> zuulStrategyRouteEntityMap = getAll();
        if (zuulStrategyRouteEntityMap == null) {
            return;
        }

        updateAll(zuulStrategyRouteEntityMap);
    }

    public Map<String, ZuulStrategyRouteEntity> getAll() {
        // 从数据库里获取路由信息
        return null;
    }
}