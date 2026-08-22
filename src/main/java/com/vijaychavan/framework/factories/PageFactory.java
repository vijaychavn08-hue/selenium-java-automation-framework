package com.vijaychavan.framework.factories;

import com.vijaychavan.framework.base.BasePage;
import com.vijaychavan.framework.driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PageFactory {
    private static final Logger log = LoggerFactory.getLogger(PageFactory.class);

    private PageFactory() {}

    public static <T extends BasePage> T getPage(Class<T> pageClass) {
        try {
            log.info("Creating page instance for: {}", pageClass.getSimpleName());
            return pageClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.error("Failed to instantiate page class: {}", pageClass.getName(), e);
            throw new RuntimeException("Error creating page object: " + pageClass.getName(), e);
        }
    }

    public static <T extends BasePage> T getPage(Class<T> pageClass, WebDriver driver) {
        try {
            return pageClass.getDeclaredConstructor(WebDriver.class).newInstance(driver);
        } catch (NoSuchMethodException e) {
            return getPage(pageClass);
        } catch (Exception e) {
            throw new RuntimeException("Error creating page object: " + pageClass.getName(), e);
        }
    }
}
