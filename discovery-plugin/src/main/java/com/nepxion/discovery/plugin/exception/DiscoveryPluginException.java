package com.nepxion.discovery.plugin.exception;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

public class DiscoveryPluginException extends RuntimeException {
    private static final long serialVersionUID = 7975167663357170655L;

    public DiscoveryPluginException() {
        super();
    }

    public DiscoveryPluginException(String message) {
        super(message);
    }

    public DiscoveryPluginException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiscoveryPluginException(Throwable cause) {
        super(cause);
    }
}