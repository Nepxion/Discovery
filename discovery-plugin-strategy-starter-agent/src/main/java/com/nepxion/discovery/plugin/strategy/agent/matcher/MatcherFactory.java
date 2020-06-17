package com.nepxion.discovery.plugin.strategy.agent.matcher;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

public class MatcherFactory {
    public static ClassMatcher newClassNameMatcher(String classInternalName) {
        return new ClassNameMatcher(classInternalName);
    }

    public static MatcherOperator newPackageBasedMatcher(String basePackageName, String interfaceName) {
        return new MatcherOperator(new PackageMatcher(basePackageName), new InterfaceMatcher(interfaceName));
    }
}