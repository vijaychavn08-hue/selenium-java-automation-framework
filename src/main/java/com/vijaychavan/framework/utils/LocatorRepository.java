package com.vijaychavan.framework.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class LocatorRepository {
    private static final Logger log = LoggerFactory.getLogger(LocatorRepository.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Map<String, JsonNode> cache = new ConcurrentHashMap<>();

    private LocatorRepository() {}

    public static By getBy(String pageName, String locatorKey) {
        JsonNode pageNode = cache.computeIfAbsent(pageName, LocatorRepository::loadPageLocators);
        if (pageNode == null || !pageNode.has(locatorKey)) {
            throw new IllegalArgumentException("Locator key '" + locatorKey + "' not found in page repository '" + pageName + "'.");
        }

        JsonNode locNode = pageNode.get(locatorKey);
        String type = locNode.path("type").asText("css").toLowerCase().trim();
        String value = locNode.path("value").asText();

        return buildBy(type, value);
    }

    private static JsonNode loadPageLocators(String pageName) {
        String resourcePath = "locators/" + pageName + ".json";
        try (InputStream is = LocatorRepository.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                log.warn("Locator file '{}' not found on classpath.", resourcePath);
                return null;
            }
            log.info("Loaded JSON locator repository for '{}'", pageName);
            return mapper.readTree(is);
        } catch (Exception e) {
            log.error("Failed to parse locator file '{}'", resourcePath, e);
            throw new RuntimeException("Error loading locator file: " + resourcePath, e);
        }
    }

    public static By buildBy(String type, String value) {
        return switch (type) {
            case "id" -> By.id(value);
            case "name" -> By.name(value);
            case "css", "cssselector" -> By.cssSelector(value);
            case "xpath" -> By.xpath(value);
            case "classname", "class" -> By.className(value);
            case "tagname", "tag" -> By.tagName(value);
            case "linktext" -> By.linkText(value);
            case "partiallinktext" -> By.partialLinkText(value);
            default -> throw new IllegalArgumentException("Unsupported locator type: " + type);
        };
    }
}
