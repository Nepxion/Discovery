package com.nepxion.discovery.plugin.strategy.starter.agent.match;


import com.nepxion.discovery.plugin.strategy.starter.agent.util.ClassInfo;

/**
 * @author emeroad
 */
public class ClassNameMatcher implements ClassMatcher {
    private final String className;

    protected ClassNameMatcher(String className) {
        this.className = className;
    }

    @Override
    public boolean match(ClassInfo classInfo) {
        return this.className.equals(classInfo.getClassName());
    }

    public String getClassName() {
        return className;
    }
}
