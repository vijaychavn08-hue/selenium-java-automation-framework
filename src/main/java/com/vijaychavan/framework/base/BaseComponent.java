package com.vijaychavan.framework.base;

import com.vijaychavan.framework.javascript.JavaScriptUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseComponent {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected final WebDriver driver;
    private final By rootLocator;
    private WebElement rootElement;

    public BaseComponent(WebDriver driver, WebElement rootElement) {
        this.driver = driver;
        this.rootElement = rootElement;
        this.rootLocator = null;
    }

    public BaseComponent(WebDriver driver, By rootLocator) {
        this.driver = driver;
        this.rootLocator = rootLocator;
        this.rootElement = null;
    }

    public WebElement getRootElement() {
        if (rootLocator != null) {
            return driver.findElement(rootLocator);
        }
        return rootElement;
    }

    protected WebElement find(By locator) {
        try {
            return getRootElement().findElement(locator);
        } catch (StaleElementReferenceException e) {
            return getRootElement().findElement(locator);
        }
    }

    protected void click(By locator) {
        try {
            WebElement element = find(locator);
            element.click();
        } catch (Exception e) {
            try {
                WebElement element = find(locator);
                JavaScriptUtil.clickWithJs(driver, element);
            } catch (Exception ex) {
                WebElement element = driver.findElement(locator);
                JavaScriptUtil.clickWithJs(driver, element);
            }
        }
    }

    protected String text(By locator) {
        try {
            WebElement el = find(locator);
            String txt = el.getText();
            if (txt == null || txt.isEmpty()) {
                txt = el.getAttribute("innerText");
            }
            if (txt == null || txt.isEmpty()) {
                txt = el.getAttribute("textContent");
            }
            return txt != null ? txt.trim() : "";
        } catch (Exception e) {
            return "";
        }
    }
}
