package com.nepxion.discovery.common;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.ConditionStrategy;
import com.nepxion.discovery.common.util.YamlUtil;

public class ConditionStrategyYamlTest {
    public static void main(String[] args) {
        String yml = testFile("sample.yaml");
        
        System.out.println("Yaml:\n" + yml);

        ConditionStrategy conditionStrategy = YamlUtil.fromYaml(yml, ConditionStrategy.class);

        System.out.println("To object:\n" + conditionStrategy);

        String yaml = YamlUtil.toYaml(conditionStrategy);

        System.out.println("To yaml:\n" + yaml);
    }

    public static String testFile(String fileName) {
        File file = new File("src/test/resources/" + fileName);
        InputStream inputStream = null;
        String rule = null;
        try {
            inputStream = new FileInputStream(file);
            rule = IOUtils.toString(inputStream, DiscoveryConstant.ENCODING_UTF_8);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                IOUtils.closeQuietly(inputStream);
            }
        }

        return rule;
    }
}