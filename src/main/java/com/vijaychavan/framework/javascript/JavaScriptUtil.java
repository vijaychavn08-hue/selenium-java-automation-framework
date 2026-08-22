package com.vijaychavan.framework.javascript;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class JavaScriptUtil {
    private static final Logger log = LoggerFactory.getLogger(JavaScriptUtil.class);

    private JavaScriptUtil() {}

    private static JavascriptExecutor getJsExecutor(WebDriver driver) {
        if (driver instanceof JavascriptExecutor js) {
            return js;
        }
        throw new IllegalStateException("Driver instance does not support JavaScriptExecutor.");
    }

    public static Object executeScript(WebDriver driver, String script, Object... args) {
        return getJsExecutor(driver).executeScript(script, args);
    }

    public static Object executeJsFile(WebDriver driver, String classpathResource, Object... args) {
        try (InputStream is = JavaScriptUtil.class.getClassLoader().getResourceAsStream(classpathResource)) {
            if (is == null) {
                throw new IllegalArgumentException("JavaScript file not found: " + classpathResource);
            }
            String script = IOUtils.toString(is, StandardCharsets.UTF_8);
            log.info("Executing external JavaScript file: {}", classpathResource);
            return getJsExecutor(driver).executeScript(script, args);
        } catch (Exception e) {
            log.error("Failed to execute JavaScript file: {}", classpathResource, e);
            throw new RuntimeException("Error executing JavaScript file: " + classpathResource, e);
        }
    }

    public static void clickWithJs(WebDriver driver, WebElement element) {
        getJsExecutor(driver).executeScript("arguments[0].click();", element);
    }

    public static void scrollToElement(WebDriver driver, WebElement element) {
        getJsExecutor(driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    public static void scrollToBottom(WebDriver driver) {
        getJsExecutor(driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public static String getInnerText(WebDriver driver, WebElement element) {
        Object result = getJsExecutor(driver).executeScript("return arguments[0].innerText;", element);
        return result != null ? result.toString() : "";
    }

    public static String getDocumentReadyState(WebDriver driver) {
        Object result = getJsExecutor(driver).executeScript("return document.readyState;");
        return result != null ? result.toString() : "";
    }
}
