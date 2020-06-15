package com.nepxion.discovery.plugin.strategy.starter.agent.match;

public class PackageOperator {
    private String packageMatcher;

    public PackageOperator(String packageMatcher) {
        this.packageMatcher = packageMatcher;
    }

    public boolean match(String className) {
        return className.startsWith(packageMatcher);
    }
}
