package com.nepxion.discovery.plugin.strategy.starter.agent.match;


import com.nepxion.discovery.plugin.strategy.starter.agent.util.ClassInfo;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author zifeihan
 * @version 1.0
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
