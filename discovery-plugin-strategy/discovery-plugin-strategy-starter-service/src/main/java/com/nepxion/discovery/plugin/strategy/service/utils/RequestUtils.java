package com.nepxion.discovery.plugin.strategy.service.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestUtils {
    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET = "GET";
    public static final String CONTENT_TYPE_JSON = "application/json";

    /**
     * 获取请求参数
     *
     * @param req
     * @return 请求参数格式key-value
     */
    public static Map<String, Object> getReqParam(HttpServletRequest req) {
        String method = req.getMethod();
        Map<String, Object> reqMap = new HashMap<>(16);
        if (METHOD_GET.equals(method)) {
            reqMap = doGet(req);
        } else if (METHOD_POST.equals(method)) {
            reqMap = doPost(req);
        } else {
            //  其他请求方式暂不处理
            return null;
        }
        return reqMap;
    }

    private static Map<String, Object> doGet(HttpServletRequest request) {
        String param = request.getQueryString();
        return paramsToMap(param);
    }

    private static Map<String, Object> doPost(HttpServletRequest request) {
        String contentType = request.getContentType();
        try {
            if (CONTENT_TYPE_JSON.equals(contentType)) {
                StringBuffer sb = new StringBuffer();
                InputStream is = request.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String s = "";
                while ((s = br.readLine()) != null) {
                    sb.append(s);
                }
                String str = sb.toString();
                return paramsToMap(str);
            } else {
                //其他内容格式的请求暂不处理
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> paramsToMap(String params) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (StringUtils.isNotBlank(params)) {
            String[] array = params.split("&");
            for (String pair : array) {
                if ("=".equals(pair.trim())) {
                    continue;
                }
                String[] entity = pair.split("=");
                if (entity.length == 1) {
                    map.put(decode(entity[0]), null);
                } else {
                    map.put(decode(entity[0]), decode(entity[1]));
                }
            }
        }
        return map;
    }

    /**
     * 编码格式转换
     *
     * @param content
     * @return
     */
    private static String decode(String content) {
        try {
            return URLDecoder.decode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}