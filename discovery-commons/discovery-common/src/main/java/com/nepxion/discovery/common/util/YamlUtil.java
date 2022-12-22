package com.nepxion.discovery.common.util;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.representer.Representer;

public class YamlUtil {
    private static DumperOptions dumperOptions = new DumperOptions();
    static {
        dumperOptions.setDefaultFlowStyle(FlowStyle.BLOCK);
    }

    public static boolean isYamlFormat(String yaml) {
        if (StringUtils.isBlank(yaml)) {
            return false;
        }

        // 非线程安全
        Yaml snakeYaml = new Yaml();
        try {
            snakeYaml.load(yaml);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static <T> String toYaml(T object) {
        // 非线程安全
        Yaml snakeYaml = new Yaml(dumperOptions);

        return snakeYaml.dump(object);
    }

    public static <T> T fromYaml(String yaml, Class<T> clazz) {
        // 非线程安全
        Yaml snakeYaml = new Yaml();

        return snakeYaml.loadAs(yaml, clazz);
    }

    public static boolean isYamlFormat(SafeConstructor safeConstructor, String yaml) {
        if (StringUtils.isBlank(yaml)) {
            return false;
        }

        // 非线程安全
        Yaml snakeYaml = new Yaml(safeConstructor);
        try {
            snakeYaml.load(yaml);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static <T> String toYaml(SafeConstructor safeConstructor, T object) {
        // 非线程安全
        Yaml snakeYaml = new Yaml(safeConstructor, new Representer(), dumperOptions);

        return snakeYaml.dump(object);
    }

    public static <T> T fromYaml(SafeConstructor safeConstructor, String yaml, Class<T> clazz) {
        // 非线程安全
        Yaml snakeYaml = new Yaml(safeConstructor);

        return snakeYaml.loadAs(yaml, clazz);
    }
}