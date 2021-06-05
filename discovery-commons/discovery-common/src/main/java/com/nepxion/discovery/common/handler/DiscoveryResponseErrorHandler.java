package com.nepxion.discovery.common.handler;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

public class DiscoveryResponseErrorHandler extends DefaultResponseErrorHandler {
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        try {
            super.handleError(response);
        } catch (Exception e) {
            DiscoveryResponseContext.getCurrentContext().setError(e);
        }
    }

    public Exception getError() {
        Exception error = DiscoveryResponseContext.getCurrentContext().getError();

        DiscoveryResponseContext.clearCurrentContext();

        return error;
    }
}