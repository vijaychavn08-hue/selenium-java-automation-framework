package com.vijaychavan.framework.factories;

import com.vijaychavan.framework.base.BaseComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ComponentFactory {
    private static final Logger log = LoggerFactory.getLogger(ComponentFactory.class);

    private ComponentFactory() {}

    public static <T extends BaseComponent> T getComponent(Class<T> componentClass, WebDriver driver, WebElement rootElement) {
        try {
            return componentClass.getDeclaredConstructor(WebDriver.class, WebElement.class).newInstance(driver, rootElement);
        } catch (Exception e) {
            log.error("Failed to instantiate component: {}", componentClass.getName(), e);
            throw new RuntimeException("Error creating component: " + componentClass.getName(), e);
        }
    }

    public static <T extends BaseComponent> T getComponent(Class<T> componentClass, WebDriver driver, By rootLocator) {
        try {
            return componentClass.getDeclaredConstructor(WebDriver.class, By.class).newInstance(driver, rootLocator);
        } catch (Exception e) {
            log.error("Failed to instantiate component with By locator: {}", componentClass.getName(), e);
            throw new RuntimeException("Error creating component: " + componentClass.getName(), e);
        }
    }
}
