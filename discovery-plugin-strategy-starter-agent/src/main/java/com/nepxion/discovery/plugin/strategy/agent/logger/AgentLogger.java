package com.nepxion.discovery.plugin.strategy.agent.logger;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;

public class AgentLogger {
    private static final AgentLogger LOG = AgentLogger.getLogger(AgentLogger.class.getName());

    private static PrintStream printStream;

    private final String messagePattern;

    static {
        try {
            printStream = System.out;
        } catch (Exception e) {
            LOG.warn("Initialize logger error:", e);
        }
    }

    public AgentLogger(String loggerName) {
        if (loggerName == null) {
            throw new NullPointerException("loggerName must not be null");
        }
        this.messagePattern = "{0,date,yyyy-MM-dd HH:mm:ss} [{1}] (" + loggerName + ") {2}{3}";
    }

    public static AgentLogger getLogger(String loggerName) {
        return new AgentLogger(loggerName);
    }

    private String format(String logLevel, String msg, String exceptionMessage) {
        exceptionMessage = defaultString(exceptionMessage, "");

        MessageFormat messageFormat = new MessageFormat(messagePattern);
        final long date = System.currentTimeMillis();
        Object[] parameter = { date, logLevel, msg, exceptionMessage };

        return messageFormat.format(parameter);
    }

    public boolean isInfoEnabled() {
        return true;
    }

    public void info(String msg) {
        String formatMessage = format("INFO", msg, "");
        printStream.println(formatMessage);
    }

    public boolean isWarnEnabled() {
        return true;
    }

    public void warn(String msg) {
        warn(msg, null);
    }

    public void warn(String msg, Throwable throwable) {
        String exceptionMessage = toString(throwable);
        String formatMessage = format("WARN", msg, exceptionMessage);
        printStream.println(formatMessage);
    }

    private String toString(Throwable throwable) {
        if (throwable == null) {
            return "";
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.println();
        throwable.printStackTrace(pw);
        pw.close();

        return sw.toString();
    }

    private String defaultString(String exceptionMessage, String defaultValue) {
        if (exceptionMessage == null) {
            return defaultValue;
        }

        return exceptionMessage;
    }
}