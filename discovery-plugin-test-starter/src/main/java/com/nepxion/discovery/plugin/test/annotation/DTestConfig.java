package com.nepxion.discovery.plugin.test.annotation;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.lang3.StringUtils;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DTestConfig {
    // 组名
    String group();

    // 服务名
    String serviceId();

    // 组名-服务名组合键值的前缀
    String prefix() default StringUtils.EMPTY;

    // 组名-服务名组合键值的后缀
    String suffix() default StringUtils.EMPTY;

    // 测试用例运行前，执行配置推送的配置文件路径。用于真正生效的配置内容
    String beforeTestPath();

    // 测试用例运行后，执行配置推送的配置文件路径。用于重置配置到初始状态，如果为空，则直接删除从配置中心删除组名-服务名组合键值
    String afterTestPath() default StringUtils.EMPTY;
}