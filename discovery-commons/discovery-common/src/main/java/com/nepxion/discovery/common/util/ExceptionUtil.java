package com.nepxion.discovery.common.util;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionUtil {
    public static ResponseEntity<String> getExceptionResponseEntity(Exception e, boolean showDetail) {
        String message = null;
        if (showDetail) {
            message = ExceptionUtils.getStackTrace(e);
        } else {
            message = e.getMessage();
        }

        // message = "An internal error occurred while processing your request\n" + message;

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
}
