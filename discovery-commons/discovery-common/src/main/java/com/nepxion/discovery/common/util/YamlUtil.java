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
import org.yaml.snakeyaml.Yaml;

public class YamlUtil {
    private static Yaml snakeYaml = new Yaml();

    public static boolean isYamlFormat(String yaml) {
        if (StringUtils.isBlank(yaml)) {
            return false;
        }

        try {
            snakeYaml.load(yaml);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Yaml getYaml() {
        return snakeYaml;
    }
}