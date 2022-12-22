package com.nepxion.discovery.common.yaml;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.util.Assert;
import org.yaml.snakeyaml.constructor.Constructor;

public class YamlSafeConstructor extends Constructor {
    private Set<String> supportedTypes;

    public YamlSafeConstructor(Set<Class<?>> supportedClasses) {
        supportedTypes = supportedClasses.stream().map(Class::getName).collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
    }

    @Override
    protected Class<?> getClassForName(String name) throws ClassNotFoundException {
        Assert.state(supportedTypes.contains(name), "Unsupported '" + name + "' type encountered in YAML document");

        return super.getClassForName(name);
    }
}