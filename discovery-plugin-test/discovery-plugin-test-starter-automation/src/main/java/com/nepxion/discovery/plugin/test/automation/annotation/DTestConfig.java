package com.nepxion.discovery.plugin.test.automation.annotation;

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

import com.nepxion.discovery.common.entity.FormatType;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DTestConfig {
    // 组名
    String group();

    // 服务名
    String serviceId();

    // 配置类型
    FormatType formatType() default FormatType.TEXT_FORMAT;

    // 组名-服务名组合键值的前缀
    String prefix() default StringUtils.EMPTY;

    // 组名-服务名组合键值的后缀
    String suffix() default StringUtils.EMPTY;

    // 执行配置的文件路径。测试用例运行前，会把该文件里的内容推送到远程配置中心或者服务
    String executePath();

    // 重置配置的文件路径。测试用例运行后，会把该文件里的内容推送到远程配置中心或者服务。该文件内容是最初的默认配置
    // 如果该注解属性为空，则直接删除从配置中心删除组名-服务名组合键值
    String resetPath() default StringUtils.EMPTY;
}