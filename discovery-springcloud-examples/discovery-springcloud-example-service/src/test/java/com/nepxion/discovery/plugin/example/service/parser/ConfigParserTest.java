package com.nepxion.discovery.plugin.example.service.parser;

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
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.plugin.framework.parser.json.JsonConfigDeparser;
import com.nepxion.discovery.plugin.framework.parser.json.JsonConfigParser;
import com.nepxion.discovery.plugin.framework.parser.xml.XmlConfigDeparser;
import com.nepxion.discovery.plugin.framework.parser.xml.XmlConfigParser;

public class ConfigParserTest {
    public static void main(String[] args) {
        testXml();
        // testJson();
    }

    public static void testXml() {
        String rule = testFile("rule.xml");

        RuleEntity ruleEntity = new XmlConfigParser().parse(rule);

        System.out.println("**************************************************");
        System.out.println(ruleEntity);
        System.out.println("**************************************************");

        System.out.println("**************************************************");
        System.out.println(new XmlConfigDeparser().deparse(ruleEntity));
        System.out.println("**************************************************");

        System.out.println("**************************************************");
        System.out.println(new JsonConfigDeparser().deparse(ruleEntity));
        System.out.println("**************************************************");
    }

    public static void testJson() {
        String rule = testFile("rule.json");

        RuleEntity ruleEntity = new JsonConfigParser().parse(rule);

        System.out.println("**************************************************");
        System.out.println(ruleEntity);
        System.out.println("**************************************************");

        System.out.println("**************************************************");
        System.out.println(new JsonConfigDeparser().deparse(ruleEntity));
        System.out.println("**************************************************");

        System.out.println("**************************************************");
        System.out.println(new XmlConfigDeparser().deparse(ruleEntity));
        System.out.println("**************************************************");
    }

    public static String testFile(String fileName) {
        File file = new File("src/main/resources/" + fileName);
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