package com.vijaychavan.framework.base;

import com.vijaychavan.framework.driver.DriverManager;
import com.vijaychavan.framework.javascript.JavaScriptUtil;
import com.vijaychavan.framework.utils.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class BasePage {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected final WebDriver driver;

    public BasePage() {
        this.driver = DriverManager.get();
    }

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void openUrl(String url) {
        log.info("Navigating to URL: {}", url);
        try {
            driver.get(url);
        } catch (Exception e) {
            log.warn("Direct navigation error: {}. Retrying via JavaScript navigation...", e.getMessage());
            try {
                JavaScriptUtil.executeScript(driver, "window.location.href = arguments[0];", url);
                WaitUtil.getWait(driver).until(d -> !d.getCurrentUrl().isBlank());
            } catch (Exception ex) {
                driver.get(url);
            }
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    protected void click(By locator) {
        log.info("Clicking element located by: {}", locator);
        WebElement element = WaitUtil.waitForClickable(driver, locator);
        try {
            element.click();
        } catch (Exception e) {
            log.info("Standard click on {} failed, attempting JS click.", locator);
            JavaScriptUtil.clickWithJs(driver, element);
        }
    }

    protected void click(WebElement element) {
        WaitUtil.waitForClickable(driver, element);
        try {
            element.click();
        } catch (Exception e) {
            JavaScriptUtil.clickWithJs(driver, element);
        }
    }

    protected void clickWithJs(By locator) {
        WebElement element = WaitUtil.waitForPresence(driver, locator);
        JavaScriptUtil.clickWithJs(driver, element);
    }

    protected void type(By locator, String value) {
        log.info("Typing text into element: {}", locator);
        WebElement element = WaitUtil.waitForVisibility(driver, locator);
        try {
            element.click();
            element.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
            if (value != null && !value.isEmpty()) {
                element.sendKeys(value);
            }
        } catch (Exception e) {
            element.clear();
            if (value != null && !value.isEmpty()) {
                element.sendKeys(value);
            }
        }
        if (value != null && !value.isEmpty()) {
            String currentVal = element.getAttribute("value");
            if (currentVal == null || !currentVal.equals(value)) {
                JavaScriptUtil.executeScript(driver,
                        "arguments[0].value = arguments[1]; " +
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true })); " +
                        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                        element, value);
            }
        }
    }

    protected String text(By locator) {
        WebElement element = WaitUtil.waitForVisibility(driver, locator);
        String txt = element.getText();
        if (txt == null || txt.isEmpty()) {
            txt = element.getAttribute("innerText");
        }
        if (txt == null || txt.isEmpty()) {
            txt = element.getAttribute("textContent");
        }
        return txt != null ? txt.trim() : "";
    }

    protected String text(WebElement element) {
        WebElement el = WaitUtil.waitForVisibility(driver, element);
        String txt = el.getText();
        if (txt == null || txt.isEmpty()) {
            txt = el.getAttribute("innerText");
        }
        if (txt == null || txt.isEmpty()) {
            txt = el.getAttribute("textContent");
        }
        return txt != null ? txt.trim() : "";
    }

    protected boolean isDisplayed(By locator) {
        try {
            return WaitUtil.waitForVisibility(driver, locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected List<WebElement> findElements(By locator) {
        return WaitUtil.waitForAllVisible(driver, locator);
    }

    protected void scrollTo(By locator) {
        WebElement element = WaitUtil.waitForPresence(driver, locator);
        JavaScriptUtil.scrollToElement(driver, element);
    }

    public abstract boolean isAt();
}
