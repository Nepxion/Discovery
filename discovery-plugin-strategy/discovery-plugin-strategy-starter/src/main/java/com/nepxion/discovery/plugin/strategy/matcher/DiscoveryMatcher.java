package com.nepxion.discovery.plugin.strategy.matcher;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.util.StringUtil;

public class DiscoveryMatcher {
    @Autowired
    protected DiscoveryMatcherStrategy discoveryMatcherStrategy;

    public boolean match(String targetValues, String value, boolean returnValue) {
        List<String> targetValueList = StringUtil.splitToList(targetValues);

        // 如果精确匹配不满足，尝试用通配符匹配
        if (targetValueList.contains(value)) {
            return returnValue;
        }

        // 通配符匹配。前者是通配表达式，后者是具体值
        for (String targetValuePattern : targetValueList) {
            if (discoveryMatcherStrategy.match(targetValuePattern, value)) {
                return returnValue;
            }
        }

        return !returnValue;
    }

    public boolean matchAddress(String addresses, String host, int port, boolean returnValue) {
        List<String> addressList = StringUtil.splitToList(addresses);

        // 如果精确匹配不满足，尝试用通配符匹配
        if (addressList.contains(host + ":" + port) || addressList.contains(host) || addressList.contains(String.valueOf(port))) {
            return returnValue;
        }

        // 通配符匹配。前者是通配表达式，后者是具体值
        for (String addressPattern : addressList) {
            if (discoveryMatcherStrategy.match(addressPattern, host + ":" + port) || discoveryMatcherStrategy.match(addressPattern, host) || discoveryMatcherStrategy.match(addressPattern, String.valueOf(port))) {
                return returnValue;
            }
        }

        return !returnValue;
    }
}