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

public class ResponseUtil {
    public static <T> ResponseEntity<T> getSuccessResponse(T result) {
        return ResponseEntity.ok().body(result);
    }

    public static <T> ResponseEntity<T> getFailureResponse(T result) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }

    public static ResponseEntity<String> getFailureResponse(Exception e) {
        String message = getFailureMessage(e);

        return getFailureResponse(message);
    }

    public static String getFailureMessage(Exception e) {
        return getFailureMessage(e, false);
    }

    public static String getFailureMessage(Exception e, boolean detailed) {
        String message = null;
        if (detailed) {
            message = ExceptionUtils.getStackTrace(e);
        } else {
            message = e.getMessage();
        }

        // message = "An internal error occurred while processing your request\n" + message;

        return message;
    }
}
