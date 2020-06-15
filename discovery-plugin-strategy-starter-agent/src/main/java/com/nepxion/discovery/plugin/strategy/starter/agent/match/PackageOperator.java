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
public class PackageOperator {
    private String packageMatcher;

    public PackageOperator(String packageMatcher) {
        this.packageMatcher = packageMatcher;
    }

    public boolean match(String className) {
        return className.startsWith(packageMatcher);
    }
}
