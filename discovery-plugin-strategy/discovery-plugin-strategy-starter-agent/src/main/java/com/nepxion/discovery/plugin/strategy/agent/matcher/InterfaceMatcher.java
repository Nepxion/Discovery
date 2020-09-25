package com.nepxion.discovery.plugin.strategy.agent.matcher;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import javassist.CtClass;

import com.nepxion.discovery.plugin.strategy.agent.util.ClassInfo;

public class InterfaceMatcher implements ClassMatcher {
    private final String interfaceName;

    public InterfaceMatcher(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    @Override
    public boolean match(ClassInfo classInfo) {
        try {
            CtClass ctClass = classInfo.getCtClass();
            CtClass[] interfaces = ctClass.getInterfaces();
            for (int i = 0; i < interfaces.length; i++) {
                CtClass anInterface = interfaces[i];
                if (interfaceName.equals(anInterface.getName())) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }
}