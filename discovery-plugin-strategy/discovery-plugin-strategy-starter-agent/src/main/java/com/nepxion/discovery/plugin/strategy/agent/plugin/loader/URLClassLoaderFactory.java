package com.nepxion.discovery.plugin.strategy.agent.plugin.loader;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import java.net.URL;
import java.net.URLClassLoader;

public class URLClassLoaderFactory {
    public static ClassLoader createClassLoader(String name, URL[] urls, ClassLoader parent) {
        return new URLClassLoader(urls, parent);
    }
}