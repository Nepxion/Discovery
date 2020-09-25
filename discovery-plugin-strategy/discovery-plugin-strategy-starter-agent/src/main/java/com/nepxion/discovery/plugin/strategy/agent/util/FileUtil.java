package com.nepxion.discovery.plugin.strategy.agent.util;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;

public final class FileUtil {
    private FileUtil() {
    }

    public static File[] listFiles(final File path, final List<String> fileExtensionList) {
        Assert.requireNonNull(path, "path");
        Assert.requireNonNull(fileExtensionList, "fileExtensionList");
        final String[] fileExtensions = fileExtensionList.toArray(new String[0]);

        return listFiles(path, fileExtensions);
    }

    public static File[] listFiles(final File path, final String[] fileExtensions) {
        Assert.requireNonNull(path, "path");
        Assert.requireNonNull(fileExtensions, "fileExtensions");

        return path.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String path = pathname.getName();
                for (String extension : fileExtensions) {
                    if (path.lastIndexOf(extension) != -1) {
                        return true;
                    }
                }

                return false;
            }
        });
    }

    public static boolean isEmpty(File[] files) {
        return files == null || files.length == 0;
    }

    public static URL toURL(final File file) throws IOException {
        Assert.requireNonNull(file, "file");

        return toURL(file, new FileFunction());
    }

    public static URL toURL(final String filePath) throws IOException {
        Assert.requireNonNull(filePath, "filePath");

        return toURL(filePath, new FilePathFunction());
    }

    public static URL[] toURLs(final File[] files) throws IOException {
        Assert.requireNonNull(files, "files");

        return toURLs(files, new FileFunction());
    }

    public static URL[] toURLs(final String[] filePaths) throws IOException {
        Assert.requireNonNull(filePaths, "filePaths");

        return toURLs(filePaths, new FilePathFunction());
    }

    private static <T> URL toURL(final T source, final Function<T, URI> function) throws IOException {
        URI uri = function.apply(source);

        return uri.toURL();
    }

    private static <T> URL[] toURLs(final T[] source, final Function<T, URI> function) throws IOException {
        final URL[] urls = new URL[source.length];
        for (int i = 0; i < source.length; i++) {
            T t = source[i];
            urls[i] = toURL(t, function);
        }

        return urls;
    }

    private interface Function<T, R> {
        R apply(T t);
    }

    private static class FileFunction implements Function<File, URI> {
        @Override
        public URI apply(File file) {
            return file.toURI();
        }
    }

    private static class FilePathFunction implements Function<String, URI> {
        @Override
        public URI apply(String filePath) {
            final File file = new File(filePath);

            return file.toURI();
        }
    }

    public static String toCanonicalPath(File file) {
        try {
            return file.getCanonicalPath();
        } catch (IOException e) {
            return file.getAbsolutePath();
        }
    }
}