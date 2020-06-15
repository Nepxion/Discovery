package com.nepxion.discovery.plugin.strategy.starter.agent.match;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author zifeihan
 * @version 1.0
 */
public class Matchers {
    public static ClassMatcher newClassNameMatcher(String classInternalName) {
        return new ClassNameMatcher(classInternalName);
    }

    public static InterfaceMatcher newPackageBasedMatcher(String basePackageName, String interfaceName) {
        return new InterfaceMatcher(basePackageName, interfaceName);
    }
}
