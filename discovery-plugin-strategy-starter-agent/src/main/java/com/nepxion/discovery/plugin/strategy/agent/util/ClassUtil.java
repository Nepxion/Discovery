package com.nepxion.discovery.plugin.strategy.agent.util;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

public class ClassUtil {
    public static String toInternalName(final String classFile) {
        return classFile.replace('/', '.');
    }
}