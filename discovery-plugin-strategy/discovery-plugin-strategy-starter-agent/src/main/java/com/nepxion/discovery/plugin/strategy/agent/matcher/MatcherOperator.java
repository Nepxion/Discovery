package com.nepxion.discovery.plugin.strategy.agent.matcher;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import com.nepxion.discovery.plugin.strategy.agent.util.ClassInfo;

public class MatcherOperator implements ClassMatcher {
    private final PackageMatcher packageMatcher;
    private final InterfaceMatcher interfaceMatcher;

    public MatcherOperator(PackageMatcher leftPackageOperator, InterfaceMatcher interfaceMatcher) {
        this.packageMatcher = leftPackageOperator;
        this.interfaceMatcher = interfaceMatcher;
    }

    public boolean match(String className, ClassLoader loader, byte[] classfileBuffer) {
        if (packageMatcher.match(className)) {
            ClassInfo classInfo = new ClassInfo(className, classfileBuffer, loader);

            return interfaceMatcher.match(classInfo);
        }

        return false;
    }

    @Override
    public boolean match(ClassInfo classInfo) {
        return false;
    }
}