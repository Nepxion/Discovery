package com.nepxion.discovery.plugin.strategy.starter.agent.transform;

import com.nepxion.discovery.plugin.strategy.starter.agent.match.ClassMatcher;
import com.nepxion.discovery.plugin.strategy.starter.agent.transformer.TransformCallback;
import com.nepxion.discovery.plugin.strategy.starter.agent.transformer.TransformTemplate;
import com.nepxion.discovery.plugin.strategy.starter.agent.util.ClassUtil;
import com.nepxion.discovery.plugin.strategy.starter.agent.util.StringUtils;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class DispatcherClassFileTransformer implements ClassFileTransformer {

    private TransformTemplate transformTemplate;

    public DispatcherClassFileTransformer(TransformTemplate transformTemplate) {
        this.transformTemplate = transformTemplate;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (StringUtils.isEmpty(className)) {
            return null;
        }

        String clazzName = ClassUtil.toInternalName(className);
        ClassMatcher classMatcher = transformTemplate.findClassMatcher(clazzName);

        if (null == classMatcher) {
            classMatcher = transformTemplate.findInterfaceMatcher(clazzName, loader, classfileBuffer);
        }
        if (null != classMatcher) {
            TransformCallback transformCallback = transformTemplate.findTransformCallback(classMatcher);
            if (null != transformCallback) {
                return transformCallback.doInTransform(loader, clazzName, classBeingRedefined, protectionDomain, classfileBuffer);
            }
        }
        return null;
    }
}
