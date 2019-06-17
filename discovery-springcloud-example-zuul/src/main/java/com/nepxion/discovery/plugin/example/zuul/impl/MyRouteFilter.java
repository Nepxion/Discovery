package com.nepxion.discovery.plugin.example.zuul.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;

import com.nepxion.discovery.plugin.strategy.zuul.filter.ZuulStrategyRouteFilter;

// 当Header中传来的用户为张三，执行一条路由路径；李四，执行另一条路由路径
public class MyRouteFilter extends ZuulStrategyRouteFilter {
    @Override
    protected String getRouteVersion() {
        String user = strategyContextHolder.getHeader("user");

        if (StringUtils.equals(user, "zhangsan")) {
            return "{\"discovery-springcloud-example-a\":\"1.0\", \"discovery-springcloud-example-b\":\"1.0\", \"discovery-springcloud-example-c\":\"1.0\"}";
        } else if (StringUtils.equals(user, "lisi")) {
            return "{\"discovery-springcloud-example-a\":\"1.1\", \"discovery-springcloud-example-b\":\"1.1\", \"discovery-springcloud-example-c\":\"1.1\"}";
        }

        return super.getRouteVersion();
    }
}