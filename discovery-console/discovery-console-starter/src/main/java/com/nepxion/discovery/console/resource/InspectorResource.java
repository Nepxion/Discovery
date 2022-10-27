package com.nepxion.discovery.console.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;
import java.util.Map;

public interface InspectorResource {
    String inspect(String protocol, String portal, String path, List<String> service, Map<String, String> header, List<String> filter);

    List<Map<String, String>> inspectToList(String protocol, String portal, String path, List<String> service, Map<String, String> header, List<String> filter);
}