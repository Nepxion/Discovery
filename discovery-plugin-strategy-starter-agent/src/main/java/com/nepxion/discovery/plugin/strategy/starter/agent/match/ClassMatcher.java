package com.nepxion.discovery.plugin.strategy.starter.agent.match;


import com.nepxion.discovery.plugin.strategy.starter.agent.util.ClassInfo;

/**
 * @author emeroad
 */
public interface ClassMatcher {
    boolean match(ClassInfo classInfo);
}
