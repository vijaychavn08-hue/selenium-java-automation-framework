package com.vijaychavan.framework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {
    private static final Logger log = LoggerFactory.getLogger(Config.class);
    private static final Properties properties = new Properties();

    static {
        try (InputStream is = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) {
                properties.load(is);
                log.info("Loaded config.properties successfully.");
            } else {
                log.warn("config.properties not found on classpath, defaulting values.");
            }
        } catch (IOException e) {
            log.error("Failed to load config.properties, using fallback defaults.", e);
        }
    }

    private Config() {}

    public static String browser() {
        String sys = System.getProperty("browser");
        if (sys != null && !sys.isBlank()) return sys.trim().toLowerCase();
        String env = System.getenv("BROWSER");
        if (env != null && !env.isBlank()) return env.trim().toLowerCase();
        return properties.getProperty("browser", "chrome").toLowerCase();
    }

    public static boolean headless() {
        String sys = System.getProperty("headless");
        if (sys != null && !sys.isBlank()) return Boolean.parseBoolean(sys.trim());
        String env = System.getenv("HEADLESS");
        if (env != null && !env.isBlank()) return Boolean.parseBoolean(env.trim());
        return Boolean.parseBoolean(properties.getProperty("headless", "true"));
    }

    public static String baseUrl() {
        String sys = System.getProperty("baseUrl");
        if (sys != null && !sys.isBlank()) return sys.trim();
        String env = System.getenv("BASE_URL");
        if (env != null && !env.isBlank()) return env.trim();
        return properties.getProperty("baseUrl", "https://www.saucedemo.com/");
    }

    public static int explicitWaitSeconds() {
        String val = properties.getProperty("explicitWait", "10");
        try {
            return Integer.parseInt(val.trim());
        } catch (NumberFormatException e) {
            return 10;
        }
    }

    public static int pageLoadTimeoutSeconds() {
        String val = properties.getProperty("pageLoadTimeout", "30");
        try {
            return Integer.parseInt(val.trim());
        } catch (NumberFormatException e) {
            return 30;
        }
    }
}
