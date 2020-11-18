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
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.nepxion.discovery.common.constant.DiscoveryConstant;

public class DiscoveryResponseErrorHandler extends DefaultResponseErrorHandler {
    private String cause;

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        // 这里绝对不能关闭InputStream
        InputStream inputStream = response.getBody();
        cause = IOUtils.toString(inputStream, DiscoveryConstant.ENCODING_UTF_8);
    }

    public String getCause() {
        return cause;
    }
}