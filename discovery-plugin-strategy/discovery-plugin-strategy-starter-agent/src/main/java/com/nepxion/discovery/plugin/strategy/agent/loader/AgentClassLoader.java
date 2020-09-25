package com.nepxion.discovery.plugin.strategy.agent.loader;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import java.net.URLClassLoader;

public class AgentClassLoader {
    @SuppressWarnings("unchecked")
    public static <T> T load(URLClassLoader currentClassLoader, ClassLoader targetClassLoader, String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        URLClassLoader urlClassLoader = new URLClassLoader(currentClassLoader.getURLs(), targetClassLoader);
        Object object = Class.forName(className, true, urlClassLoader).newInstance();

        return (T) object;
    }
}
