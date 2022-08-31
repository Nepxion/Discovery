package com.nepxion.discovery.common.util;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.nepxion.discovery.common.json.DiscoveryPrettyPrinter;

public class JsonUtil {
    private static ObjectMapper objectMapper;
    private static DiscoveryPrettyPrinter discoveryPrettyPrinter;

    static {
        objectMapper = new ObjectMapper();
        // objectMapper.getSerializerProvider().setNullKeySerializer(new NullKeySerializer());
        // objectMapper.setDateFormat(new SimpleDateFormat(DiscoveryConstant.DATE_FORMAT));

        discoveryPrettyPrinter = new DiscoveryPrettyPrinter();
    }

    public static class NullKeySerializer extends StdSerializer<Object> {
        private static final long serialVersionUID = -9176767187240330396L;

        public NullKeySerializer() {
            this(null);
        }

        public NullKeySerializer(Class<Object> object) {
            super(object);
        }

        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeFieldName(StringUtils.EMPTY);
        }
    }

    public static <T> String toJson(T object) {
        if (object == null) {
            throw new IllegalArgumentException("Object is null");
        }

        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static <T> String toPrettyJson(T object) {
        if (object == null) {
            throw new IllegalArgumentException("Object is null");
        }

        try {
            return objectMapper.writer(discoveryPrettyPrinter).writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            throw new IllegalArgumentException("Json is null or empty");
        }

        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(json)) {
            throw new IllegalArgumentException("Json is null or empty");
        }

        try {
            return objectMapper.readValue(json, typeReference);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static boolean isJsonFormat(String json) {
        if (StringUtils.isBlank(json)) {
            return false;
        }

        try {
            objectMapper.readTree(json);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public static String fromJsonMap(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        String value = null;
        try {
            Map<String, String> map = fromJson(json, Map.class);
            value = map.get(key);
        } catch (Exception e) {
            value = json;
        }

        return value;
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}