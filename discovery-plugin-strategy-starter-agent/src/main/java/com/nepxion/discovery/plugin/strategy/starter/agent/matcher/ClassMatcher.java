package com.nepxion.discovery.plugin.strategy.starter.agent.matcher;

import com.nepxion.discovery.plugin.strategy.starter.agent.util.ClassInfo;

public interface ClassMatcher {
    boolean match(ClassInfo classInfo);
}