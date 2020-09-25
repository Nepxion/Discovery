package com.nepxion.discovery.console.desktop.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.swing.exception.ExceptionTracerContext;
import com.nepxion.util.encoder.EncoderContext;
import com.nepxion.util.locale.LocaleContext;

public class DataContext {
    public static final String CHARSET = "UTF-8";
    public static final String LOCALE = "zh_CN";

    public static void initialize() {
        initializeEncoder();
        initializeLocale();
        initializeTracer();
    }

    private static void initializeEncoder() {
        EncoderContext.registerIOCharset(CHARSET);
    }

    private static void initializeLocale() {
        LocaleContext.registerLocale(LOCALE);
    }

    private static void initializeTracer() {
        ExceptionTracerContext.register(true);
    }
}