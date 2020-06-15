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
public class MatcherOperator {
    private final PackageOperator leftOperand;
    private final InterfaceMatcher interfaceMatcher;

    public MatcherOperator(PackageOperator leftOperand, InterfaceMatcher interfaceMatcher) {
        this.leftOperand = leftOperand;
        this.interfaceMatcher = interfaceMatcher;
    }

    public boolean match(String className, ClassLoader loader, byte[] classfileBuffer) {
        if (leftOperand.match(className)) {
            ClassInfo classInfo = new ClassInfo(className, classfileBuffer, loader);
            return interfaceMatcher.match(classInfo);
        }
        return false;
    }
}
