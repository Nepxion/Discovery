package com.nepxion.discovery.plugin.strategy.agent.loader;

import java.net.URLClassLoader;

public class AgentClassLoader {

    public static <T> T load(URLClassLoader currentClassLoader, ClassLoader targetClassLoader, String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        URLClassLoader urlClassLoader = new URLClassLoader(currentClassLoader.getURLs(), targetClassLoader);
        Object o = Class.forName(className, true, urlClassLoader).newInstance();
        return (T) o;
    }
}
