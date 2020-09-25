package com.nepxion.discovery.common.util;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtil {
    public static InputStream getInputStream(String path) throws IOException {
        // 从Resource路径获取
        InputStream inputStream = IOUtil.class.getClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            // 从文件路径获取
            inputStream = new FileInputStream(path);
        }

        return inputStream;
    }
}