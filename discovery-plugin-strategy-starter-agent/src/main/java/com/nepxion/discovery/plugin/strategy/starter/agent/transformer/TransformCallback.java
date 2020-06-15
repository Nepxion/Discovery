package com.nepxion.discovery.plugin.strategy.starter.agent.transformer;

import java.security.ProtectionDomain;

public interface TransformCallback {

    byte[] doInTransform(ClassLoader classLoader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer);
}
