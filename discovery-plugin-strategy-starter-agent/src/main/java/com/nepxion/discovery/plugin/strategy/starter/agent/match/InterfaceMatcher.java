package com.nepxion.discovery.plugin.strategy.starter.agent.match;

import com.nepxion.discovery.plugin.strategy.starter.agent.util.ClassInfo;
import javassist.CtClass;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author zifeihan
 * @version 1.0
 */
public class InterfaceMatcher implements ClassMatcher {

    private final String basePackageName;
    private final String interfaceName;

    public InterfaceMatcher(String basePackageName, String interfaceName) {
        this.basePackageName = basePackageName;
        this.interfaceName = interfaceName;
    }

    @Override
    public boolean match(ClassInfo classInfo) {
        try {
            if (!classInfo.getClassName().startsWith(basePackageName)) {
                return false;
            }
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

    public String getBasePackageName() {
        return basePackageName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }
}
