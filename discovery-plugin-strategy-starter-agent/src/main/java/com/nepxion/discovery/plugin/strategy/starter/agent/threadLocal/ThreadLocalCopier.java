package com.nepxion.discovery.plugin.strategy.starter.agent.threadLocal;


import com.nepxion.discovery.plugin.strategy.starter.agent.log.SampleLogger;
import com.nepxion.discovery.plugin.strategy.starter.agent.plugin.thread.ThreadLocalHook;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author zifeihan
 * @version 1.0
 */
public class ThreadLocalCopier {

    private static List<ThreadLocalHook> threadHooks = new ArrayList<>();

    private static final SampleLogger LOG = SampleLogger.getLogger(ThreadLocalCopier.class.getName());

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
            LOG.warn("create(construct) method for thread hook error, message:", e);
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
            LOG.warn("before(run/call) method for thread hook error, message:", e);
        }
    }

    public static void after() {
        try {
            for (int i = 0; i < threadHooks.size(); i++) {
                ThreadLocalHook threadHook = threadHooks.get(i);
                threadHook.after();
            }
        } catch (Exception e) {
            LOG.warn("after(run/call) method for thread hook error, message:", e);
        }
    }
}
