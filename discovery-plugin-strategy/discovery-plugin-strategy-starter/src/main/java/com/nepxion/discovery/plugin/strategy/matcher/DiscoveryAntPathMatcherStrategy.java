package com.nepxion.discovery.plugin.strategy.matcher;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.util.AntPathMatcher;

public class DiscoveryAntPathMatcherStrategy implements DiscoveryMatcherStrategy {
    private AntPathMatcher matcher = new AntPathMatcher();

    @Override
    public boolean match(String pattern, String value) {
        return matcher.match(pattern, value);
    }
}