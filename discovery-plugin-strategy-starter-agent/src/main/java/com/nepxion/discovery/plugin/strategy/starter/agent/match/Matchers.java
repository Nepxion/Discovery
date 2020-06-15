package com.nepxion.discovery.plugin.strategy.starter.agent.match;

public class Matchers {
    public static ClassMatcher newClassNameMatcher(String classInternalName) {
        return new ClassNameMatcher(classInternalName);
    }

    public static InterfaceMatcher newPackageBasedMatcher(String basePackageName, String interfaceName) {
        return new InterfaceMatcher(basePackageName, interfaceName);
    }
}
