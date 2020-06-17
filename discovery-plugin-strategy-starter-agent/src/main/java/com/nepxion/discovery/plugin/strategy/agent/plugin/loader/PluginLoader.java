package com.nepxion.discovery.plugin.strategy.agent.plugin.loader;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class PluginLoader {
    public static <T> List<T> load(ClassLoader classLoader, Class<T> clazz) {
        List<T> profilerPlugins = new ArrayList<T>();
        ServiceLoader<T> serviceLoader = ServiceLoader.load(clazz, classLoader);
        for (T t : serviceLoader) {
            profilerPlugins.add(t);
        }

        return profilerPlugins;
    }
}