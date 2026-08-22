package com.vijaychavan.framework.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public final class JsonUtil {
    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    private JsonUtil() {}

    public static JsonNode readClasspathJson(String relativePath) {
        try (InputStream is = JsonUtil.class.getClassLoader().getResourceAsStream(relativePath)) {
            if (is == null) {
                throw new IllegalArgumentException("JSON file not found on classpath: " + relativePath);
            }
            return mapper.readTree(is);
        } catch (Exception e) {
            log.error("Failed to read JSON from {}", relativePath, e);
            throw new RuntimeException("Error reading JSON file: " + relativePath, e);
        }
    }

    public static <T> T readClasspathJson(String relativePath, Class<T> clazz) {
        try (InputStream is = JsonUtil.class.getClassLoader().getResourceAsStream(relativePath)) {
            if (is == null) {
                throw new IllegalArgumentException("JSON file not found on classpath: " + relativePath);
            }
            return mapper.readValue(is, clazz);
        } catch (Exception e) {
            log.error("Failed to parse JSON from {}", relativePath, e);
            throw new RuntimeException("Error parsing JSON to " + clazz.getSimpleName(), e);
        }
    }
}
