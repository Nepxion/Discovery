package com.nepxion.discovery.plugin.strategy.agent.matcher;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

public class PackageMatcher {
    private String packageName;

    public PackageMatcher(String packageName) {
        this.packageName = packageName;
    }

    public boolean match(String className) {
        return className.startsWith(packageName);
    }
}