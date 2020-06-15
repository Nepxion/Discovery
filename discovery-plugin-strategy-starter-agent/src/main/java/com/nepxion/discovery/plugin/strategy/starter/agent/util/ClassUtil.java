package com.nepxion.discovery.plugin.strategy.starter.agent.util;

public class ClassUtil {

    public static String toInternalName(final String classFile) {
        return classFile.replace('/', '.');
    }
}
