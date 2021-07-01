package com.nepxion.discovery.common.util;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.nepxion.discovery.common.constant.DiscoveryConstant;

public class FileUtil {
    private static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);

    public static File getFile(ApplicationContext applicationContext, String path) {
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("File path doesn't set");
        }

        try {
            String filePath = applicationContext.getEnvironment().resolvePlaceholders(path);

            File file = applicationContext.getResource(filePath).getFile();

            LOG.info("File [{}] is found", path);

            return file;
        } catch (Exception e) {
            LOG.warn("File [{}] isn't found or valid, ignore to load...", path);
        }

        return null;
    }

    public static InputStream getInputStream(ApplicationContext applicationContext, String path) {
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("File path doesn't set");
        }

        try {
            String filePath = applicationContext.getEnvironment().resolvePlaceholders(path);

            InputStream inputStream = applicationContext.getResource(filePath).getInputStream();

            LOG.info("File [{}] is found", path);

            return inputStream;
        } catch (Exception e) {
            LOG.warn("File [{}] isn't found or valid, ignore to load...", path);
        }

        return null;
    }

    public static String getText(ApplicationContext applicationContext, String path) {
        InputStream inputStream = null;
        try {
            inputStream = getInputStream(applicationContext, path);
            if (inputStream != null) {
                try {
                    return IOUtils.toString(inputStream, DiscoveryConstant.ENCODING_UTF_8);
                } catch (IOException e) {
                    LOG.warn("InputStream to String failed, ignore to load...");
                }
            }
        } finally {
            if (inputStream != null) {
                IOUtils.closeQuietly(inputStream);
            }
        }

        return null;
    }

    public static void toFile(String text, String directoryPath, String fileName, String encoding) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            directory.mkdirs();
        }

        File file = new File(directoryPath + File.separator + fileName);
        FileUtils.writeStringToFile(file, text, encoding);
    }

    public static void toFile(byte[] bytes, String directoryPath, String fileName) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            directory.mkdirs();
        }

        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            File file = new File(directoryPath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } finally {
            if (bos != null) {
                IOUtils.closeQuietly(bos);
            }
            if (fos != null) {
                IOUtils.closeQuietly(fos);
            }
        }
    }

    public static byte[] fromFile(String directoryPath, String fileName) throws IOException {
        File file = new File(directoryPath + File.separator + fileName);

        return FileUtils.readFileToByteArray(file);
    }

    public static void forceDeleteDirectory(File directory, int forceTimes) {
        if (directory.isDirectory() && directory.exists()) {
            try {
                FileUtils.deleteDirectory(directory);
                forceTimes--;
                if (forceTimes > 0) {
                    forceDeleteDirectory(directory, forceTimes);
                } else {
                    throw new IOException("Force delete directory=" + directory + " failed");
                }
            } catch (IOException e) {

            }
        }
    }

    public static void forceDeleteFile(File file, int forceTimes) {
        if (file.isFile() && file.exists()) {
            try {
                FileUtils.deleteQuietly(file);
                forceTimes--;
                if (forceTimes > 0) {
                    forceDeleteFile(file, forceTimes);
                } else {
                    throw new IOException("Force delete file=" + file + " failed");
                }
            } catch (IOException e) {

            }
        }
    }

    public static byte[] getBytes(File file) throws FileNotFoundException, IOException {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);

            return IOUtils.toByteArray(inputStream);
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            if (inputStream != null) {
                IOUtils.closeQuietly(inputStream);
            }
        }
    }
}