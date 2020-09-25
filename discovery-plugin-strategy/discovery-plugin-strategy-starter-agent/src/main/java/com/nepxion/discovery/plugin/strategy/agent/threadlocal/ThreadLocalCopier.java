package com.nepxion.discovery.plugin.strategy.agent.threadlocal;

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

import com.nepxion.discovery.plugin.strategy.agent.logger.AgentLogger;

public class ThreadLocalCopier {
    private static final AgentLogger LOG = AgentLogger.getLogger(ThreadLocalCopier.class.getName());
    private static List<ThreadLocalHook> threadHooks = new ArrayList<>();

    public static void register(ThreadLocalHook threadHook) {
        threadHooks.add(threadHook);
    }

    public static Object[] create() {
        Object[] objects = new Object[threadHooks.size()];
        try {
            for (int i = 0; i < objects.length; i++) {
                ThreadLocalHook threadHook = threadHooks.get(i);
                objects[i] = threadHook.create();
            }
        } catch (Exception e) {
            LOG.warn("Execute create(construct) method for thread hook error, message:", e);
        }

        return objects;
    }

    public static void before(Object[] objects) {
        try {
            for (int i = 0; i < objects.length; i++) {
                ThreadLocalHook threadHook = threadHooks.get(i);
                threadHook.before(objects[i]);
            }
        } catch (Exception e) {
            LOG.warn("Execute before(run/call) method for thread hook error, message:", e);
        }
    }

    public static void after() {
        try {
            for (int i = 0; i < threadHooks.size(); i++) {
                ThreadLocalHook threadHook = threadHooks.get(i);
                threadHook.after();
            }
        } catch (Exception e) {
            LOG.warn("Execute after(run/call) method for thread hook error, message:", e);
        }
    }
}